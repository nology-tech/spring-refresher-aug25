package io.nology.library.book;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.nology.library.book.dtos.CreateBookDTO;
import io.nology.library.book.dtos.UpdateBookDTO;
import io.nology.library.book.entities.Book;
import jakarta.validation.Valid;

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

    public Book create(CreateBookDTO data) {
        Book newBook = new Book();
        newBook.setAuthor(data.getAuthor().trim());
        newBook.setTitle(data.getTitle().trim());
        newBook.setGenre(data.getGenre());
        newBook.setYearPublished(data.getYearPublished());
        return this.bookRepository.saveAndFlush(newBook);
    }

    public void deleteBook(Book book) {
        this.bookRepository.delete(book);
    }

    public Boolean deleteById(Long id) {
        Optional<Book> result = this.findById(id);
        if (result.isEmpty()) {
            return false;
        }
        this.bookRepository.delete(result.get());
        return true;
    }

    public Optional<Book> updateById(Long id, UpdateBookDTO data) {
        Optional<Book> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        Book toUpdate = result.get();

        if (data.getAuthor() != null) {
            toUpdate.setAuthor(data.getAuthor().trim());
        }

        if (data.getTitle() != null) {
            toUpdate.setTitle(data.getTitle().trim());
        }
        if (data.getGenre() != null) {
            toUpdate.setGenre(data.getGenre());
        }

        if (data.getYearPublished() != null) {
            toUpdate.setYearPublished(data.getYearPublished());
        }

        this.bookRepository.save(toUpdate);
        return Optional.of(toUpdate);

    }

}
