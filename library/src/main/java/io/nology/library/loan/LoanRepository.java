package io.nology.library.loan;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.library.loan.entities.Loan;

import io.nology.library.member.entities.Member;

import io.nology.library.book.entities.Book;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    long countByMemberAndReturnedAtIsNull(Member member);

    boolean existsByBookAndReturnedAtIsNull(Book book);

    @EntityGraph(attributePaths = { "book", "member", "book.genre" })
    Optional<Loan> findById(Long id);
}
