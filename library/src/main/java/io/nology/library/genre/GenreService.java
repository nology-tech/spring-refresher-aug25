package io.nology.library.genre;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.nology.library.genre.entities.Genre;

@Service
public class GenreService {
    private final GenreRepository genreRepo;

    public GenreService(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    public Optional<Genre> findById(Long id) {
        return this.genreRepo.findById(id);
    }

}
