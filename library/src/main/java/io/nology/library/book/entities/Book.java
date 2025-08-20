package io.nology.library.book.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    public enum Genre {
        HORROR,
        FANTASY,
        SCIENCE_FICTION,
        ROMANCE,
        NON_FICTION,
        ADVENTURE,
        DRAMA,
        FICTION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String author;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column
    private Integer yearPublished;

    public Book(String author, String title, Genre genre, Integer yearPublished) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.yearPublished = yearPublished;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setYearPublished(Integer yearPublished) {
        this.yearPublished = yearPublished;
    }

}
