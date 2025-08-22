package io.nology.library.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.library.book.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    // @Query("SELECT b FROM Book b JOIN FETCH b.genre")
    @EntityGraph(attributePaths = { "genre" })
    List<Book> findAll();

    @EntityGraph(attributePaths = { "genre" })
    Optional<Book> findById(Long id);
}
