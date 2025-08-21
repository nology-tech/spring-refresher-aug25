package io.nology.library.book.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateBookDTO {

    @NotBlank
    private String author;

    @NotBlank
    private String title;

    @NotNull
    @Min(1)
    private Long genreId;

    @NotNull
    @Min(0)
    private Integer yearPublished;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

    public Long getGenreId() {
        return genreId;
    }

}
