package io.nology.library.book;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.nology.library.book.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    // @Query("SELECT b FROM Book b JOIN FETCH b.genre")
    @EntityGraph(attributePaths = { "genre" })
    List<Book> findAll();
}
