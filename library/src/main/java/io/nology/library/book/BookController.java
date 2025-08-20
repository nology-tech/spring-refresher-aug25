package io.nology.library.book;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.library.book.dtos.CreateBookDTO;
import io.nology.library.book.entities.Book;
import io.nology.library.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        Book book = this.bookService.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find book with id " + id));
        return ResponseEntity.ok(book);
    }

}
