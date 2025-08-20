package io.nology.library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.library.book.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
