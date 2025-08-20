package io.nology.library.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.nology.library.book.BookRepository;
import io.nology.library.book.entities.Book;

@Component
@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {

    private final BookRepository bookRepo;

    public DevDataSeeder(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepo.count() == 0) {
            Book theRavenTower = new Book("Ann Leckie", "The Raven Tower", Book.Genre.FANTASY, 2019);
            bookRepo.saveAndFlush(theRavenTower);
            Book houseOfLeaves = new Book("Mark Z. Danielewski", "House of Leaves", Book.Genre.HORROR, 2000);
            bookRepo.saveAndFlush(houseOfLeaves);
            Book demonCopperhead = new Book("Barbara Kingsolver", "Demon Copperhead", Book.Genre.FICTION, 2022);
            bookRepo.saveAndFlush(demonCopperhead);
        }
    }

}
