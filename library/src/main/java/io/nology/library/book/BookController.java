package io.nology.library.book;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.library.book.dtos.CreateBookDTO;
import io.nology.library.book.dtos.UpdateBookDTO;
import io.nology.library.book.entities.Book;
import io.nology.library.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody @Valid CreateBookDTO data) {
        Book createdBook = this.bookService.create(data);
        return new ResponseEntity<Book>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = this.bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) throws NotFoundException {
        Book book = this.findBookById(id);
        return ResponseEntity.ok(book);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateById(@PathVariable Long id, @RequestBody @Valid UpdateBookDTO data)
            throws NotFoundException {
        Book updatedBook = this.bookService.updateById(id, data)
                .orElseThrow(() -> new NotFoundException("Could not find book with id " + id));
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws NotFoundException {
        // Sometimes you may want to return the thing you just deleted
        // - particularly to save on HTTP requests in the front end
        // Book book = this.findBookById(id);
        // this.bookService.deleteBook(book);
        // return ResponseEntity.ok(book);

        Boolean deleted = this.bookService.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Could not find book with id " + id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    private Book findBookById(Long id) throws NotFoundException {
        return this.bookService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find book with id " + id));
    }
}
