package io.nology.library.book.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class UpdateBookDTO {

    @Pattern(regexp = ".*\\S.*", message = "Author should not be blank")
    private String author;

    @Pattern(regexp = ".*\\S.*", message = "Title should not be blank")
    private String title;

    @Min(1)
    private Long genreId;

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
