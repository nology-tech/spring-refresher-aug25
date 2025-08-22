package io.nology.library.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.library.genre.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
