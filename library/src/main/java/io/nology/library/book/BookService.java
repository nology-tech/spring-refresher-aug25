package io.nology.library.book;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.nology.library.book.dtos.CreateBookDTO;
import io.nology.library.book.dtos.UpdateBookDTO;
import io.nology.library.book.entities.Book;
import io.nology.library.common.ValidationErrors;
import io.nology.library.common.exceptions.BadRequestException;
import io.nology.library.common.exceptions.NotFoundException;
import io.nology.library.genre.GenreService;
import io.nology.library.genre.entities.Genre;
import jakarta.validation.Valid;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;
    private final GenreService genreService;

    public BookService(BookRepository bookRepository, ModelMapper mapper, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.genreService = genreService;
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    public Book create(CreateBookDTO data) throws Exception {
        // Book newBook = new Book();
        // newBook.setAuthor(data.getAuthor().trim());
        // newBook.setTitle(data.getTitle().trim());
        // newBook.setGenre(data.getGenre());
        // newBook.setYearPublished(data.getYearPublished());
        ValidationErrors errors = new ValidationErrors();
        // Genre genre = this.findGenreOrThrow(data.getGenreId());
        var result = this.genreService.findById(data.getGenreId());
        if (result.isEmpty()) {
            errors.add("genre", "invalid id");
        }

        Book newBook = mapper.map(data, Book.class);
        if (errors.hasErrors()) {
            throw new BadRequestException("Validation Failed", errors);
        }
        newBook.setGenre(result.get());
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

    public Optional<Book> updateById(Long id, @Valid UpdateBookDTO data) throws BadRequestException {
        Optional<Book> result = this.findById(id);
        if (result.isEmpty()) {
            return result;
        }
        ValidationErrors errors = new ValidationErrors();
        Book toUpdate = result.get();
        var genreResult = this.genreService.findById(data.getGenreId());
        if (genreResult.isEmpty()) {
            errors.add("genre", "invalid id");
        }

        // if (data.getAuthor() != null) {
        // toUpdate.setAuthor(data.getAuthor().trim());
        // }

        // if (data.getTitle() != null) {
        // toUpdate.setTitle(data.getTitle().trim());
        // }
        // if (data.getGenre() != null) {
        // toUpdate.setGenre(data.getGenre());
        // }

        // if (data.getYearPublished() != null) {
        // toUpdate.setYearPublished(data.getYearPublished());
        // }
        mapper.map(data, toUpdate);
        if (errors.hasErrors()) {
            throw new BadRequestException("validation failed", errors);
        }
        toUpdate.setGenre(genreResult.get());
        this.bookRepository.save(toUpdate);
        return Optional.of(toUpdate);

    }

    // private Genre findGenreOrThrow(Long id) throws BadRequestException {
    // return this.genreService.findById(id)
    // .orElseThrow(() -> new BadRequestException("Could not find genre with id " +
    // id));

    // }

}
