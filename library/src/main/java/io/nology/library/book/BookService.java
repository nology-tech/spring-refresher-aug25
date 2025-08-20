package io.nology.library.book;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.nology.library.book.entities.Book;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

}
