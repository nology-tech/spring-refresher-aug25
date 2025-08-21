package io.nology.library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.library.book.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
