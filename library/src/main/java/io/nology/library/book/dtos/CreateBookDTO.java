package io.nology.library.book.dtos;

import io.nology.library.book.entities.Book.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateBookDTO {

    @NotBlank
    private String author;

    @NotBlank
    private String title;

    @NotNull
    private Genre genre;

    @NotNull
    @Min(0)
    private Integer yearPublished;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

}
