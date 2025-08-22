package io.nology.library.book;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.library.book.dtos.BookResponseDTO;
import io.nology.library.book.dtos.CreateBookDTO;
import io.nology.library.book.dtos.UpdateBookDTO;
import io.nology.library.book.entities.Book;
import io.nology.library.common.exceptions.BadRequestException;
import io.nology.library.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

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
    private final ModelMapper mapper;

    public BookController(BookService bookService, ModelMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PostMapping()
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid CreateBookDTO data) throws Exception {
        Book createdBook = this.bookService.create(data);
        BookResponseDTO response = mapper.map(createdBook, BookResponseDTO.class);
        return new ResponseEntity<BookResponseDTO>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = this.bookService.getAllBooks().stream()
                .map(b -> mapper.map(b, BookResponseDTO.class)).toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) throws NotFoundException {
        Book book = this.findBookById(id);
        BookResponseDTO response = mapper.map(book, BookResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateById(@PathVariable Long id, @RequestBody @Valid UpdateBookDTO data)
            throws NotFoundException, BadRequestException {
        Book updatedBook = this.bookService.updateById(id, data)
                .orElseThrow(() -> new NotFoundException("Could not find book with id " + id));
        BookResponseDTO response = mapper.map(updatedBook, BookResponseDTO.class);
        return ResponseEntity.ok(response);
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
