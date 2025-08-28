package io.nology.library.loan;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.library.common.exceptions.BadRequestException;
import io.nology.library.common.exceptions.NotFoundException;
import io.nology.library.loan.dtos.CreateLoanDTO;
import io.nology.library.loan.dtos.ReturnLoanDTO;
import io.nology.library.loan.entities.Loan;
import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping()
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody CreateLoanDTO data) throws BadRequestException {
        Loan newLoan = this.loanService.createLoan(data);
        return new ResponseEntity<Loan>(newLoan, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Loan> returnLoan(@PathVariable Long id, @Valid @RequestBody ReturnLoanDTO data)
            throws BadRequestException, NotFoundException {
        Loan result = this.loanService.returnLoan(id, data)
                .orElseThrow(() -> new NotFoundException("Loan with id " + id + " does not exist"));
        return new ResponseEntity<Loan>(result, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> findById(@PathVariable Long id) throws NotFoundException {
        Loan result = this.loanService.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan with id " + id + " does not exist"));
        System.out.println(result.getBook().getGenre());
        System.err.println(result.getMember().getFirstName());
        return ResponseEntity.ok(result);
    }

}
