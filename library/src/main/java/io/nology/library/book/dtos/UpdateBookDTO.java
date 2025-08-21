package io.nology.library.book.dtos;

import io.nology.library.book.entities.Book.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class UpdateBookDTO {

    @Pattern(regexp = ".*\\S.*", message = "Author should not be blank")
    private String author;

    @Pattern(regexp = ".*\\S.*", message = "Title should not be blank")
    private String title;

    private Genre genre;

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

    @Override
    public String toString() {
        return "UpdateBookDTO [author=" + author + ", title=" + title + ", genre=" + genre + ", yearPublished="
                + yearPublished + "]";
    }

}
