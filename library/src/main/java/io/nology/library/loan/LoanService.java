package io.nology.library.loan;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.nology.library.book.BookService;
import io.nology.library.book.entities.Book;
import io.nology.library.common.ValidationErrors;
import io.nology.library.common.exceptions.BadRequestException;
import io.nology.library.loan.dtos.CreateLoanDTO;
import io.nology.library.loan.dtos.ReturnLoanDTO;
import io.nology.library.loan.entities.Loan;
import io.nology.library.member.MemberService;
import io.nology.library.member.entities.Member;
import jakarta.validation.Valid;

@Service
public class LoanService {
    private final BookService bookService;
    private final MemberService memberService;
    private final LoanRepository loanRepository;

    public LoanService(BookService bookService, MemberService memberService, LoanRepository loanRepository) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(CreateLoanDTO data) throws BadRequestException {
        ValidationErrors errors = new ValidationErrors();
        // check if the member exists
        Member foundMember = this.memberService.findById(data.getMemberId()).orElse(null);
        if (foundMember == null) {
            errors.add("member", "could not find member with id " + data.getMemberId());
        }
        // check if the book exists
        Book foundBook = this.bookService.findById(data.getBookId()).orElse(null);
        if (foundBook == null) {
            errors.add("book", "could not find book with id " + data.getBookId());
        }
        // check if the member has too many books on loan
        if (foundMember != null) {
            long activeLoans = this.loanRepository.countByMemberAndReturnedAtIsNull(foundMember);
            if (activeLoans >= 5) {
                errors.add("member", "currently at maximum loan limit");
            }
        }

        // check that the book is not on loan
        if (foundBook != null) {
            boolean isOnLoan = this.loanRepository.existsByBookAndReturnedAtIsNull(foundBook);
            if (isOnLoan) {
                errors.add("book", "book is already on loan");
            }
        }
        if (errors.hasErrors()) {
            throw new BadRequestException("validation failed", errors);
        }

        // create loan
        Loan newLoan = new Loan();
        newLoan.setBook(foundBook);
        newLoan.setMember(foundMember);
        newLoan.setBorrowedAt(LocalDate.now());
        this.loanRepository.saveAndFlush(newLoan);

        return newLoan;
    }

    public Optional<Loan> returnLoan(Long id, ReturnLoanDTO data) throws BadRequestException {
        Optional<Loan> result = this.loanRepository.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        ValidationErrors errors = new ValidationErrors();
        Loan foundLoan = result.get();
        if (foundLoan.getReturnedAt() != null) {
            errors.add("loan", "this loan has already been returned");
        }
        Book loanedBook = foundLoan.getBook();
        Book inputBook = this.bookService.findById(data.getBookId()).orElse(null);
        if (loanedBook != inputBook) {
            errors.add("book", "input book does not match loan book");
        }
        if (errors.hasErrors()) {
            throw new BadRequestException("validation failed", errors);
        }
        foundLoan.setReturnedAt(LocalDate.now());
        this.loanRepository.saveAndFlush(foundLoan);
        return Optional.of(foundLoan);

    }

    public Optional<Loan> findById(Long id) {
        return this.loanRepository.findById(id);
    }

}
