package io.nology.library.seeder;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.nology.library.book.BookRepository;
import io.nology.library.book.entities.Book;
import io.nology.library.genre.GenreRepository;
import io.nology.library.genre.entities.Genre;

@Component
@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {

    private final BookRepository bookRepo;
    private final GenreRepository genreRepo;

    public DevDataSeeder(BookRepository bookRepo, GenreRepository genreRepo) {
        this.bookRepo = bookRepo;
        this.genreRepo = genreRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (genreRepo.count() == 0) {
            Genre fantasy = new Genre("Fantasy");
            Genre sciFi = new Genre("Science Fiction");
            Genre horror = new Genre("Horror");
            Genre nonFiction = new Genre("Non Fiction");
            Genre fiction = new Genre("Fiction");
            genreRepo.saveAll(Arrays.asList(fantasy, sciFi, horror, nonFiction, fiction));
            if (bookRepo.count() == 0) {
                Book theRavenTower = new Book("Ann Leckie", "The Raven Tower", fantasy, 2019);
                Book houseOfLeaves = new Book("Mark Z. Danielewski", "House of Leaves", horror, 2000);
                Book demonCopperhead = new Book("Barbara Kingsolver", "Demon Copperhead", nonFiction, 2022);
                bookRepo.saveAll(Arrays.asList(theRavenTower, houseOfLeaves, demonCopperhead));
            }
        }
    }

}
