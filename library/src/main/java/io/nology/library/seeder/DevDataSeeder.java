package io.nology.library.seeder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import io.nology.library.book.BookRepository;
import io.nology.library.book.entities.Book;
import io.nology.library.genre.GenreRepository;
import io.nology.library.genre.entities.Genre;
import io.nology.library.member.MemberRepository;
import io.nology.library.member.entities.Member;

@Component
@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {

    private final BookRepository bookRepo;
    private final GenreRepository genreRepo;
    private final MemberRepository memberRepository;
    private final Faker faker = new Faker();

    public DevDataSeeder(BookRepository bookRepo, GenreRepository genreRepo, MemberRepository memberRepository) {
        this.bookRepo = bookRepo;
        this.genreRepo = genreRepo;
        this.memberRepository = memberRepository;
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
                for (int i = 0; i < 20; i++) {
                    String author = faker.book().author();
                    String title = faker.book().title();
                    Integer yearPublished = faker.number().numberBetween(1600, 2025);
                    Long genreId = faker.number().numberBetween(1L, 5L);
                    Genre genre = new Genre(genreId);
                    Book fakeBook = new Book(author, title, genre, yearPublished);
                    this.bookRepo.saveAndFlush(fakeBook);
                }
            }

            if (memberRepository.count() == 0) {
                Set<String> emails = new HashSet<>();
                Set<String> phoneNumbers = new HashSet<>();
                for (int i = 0; i < 20; i++) {
                    String firstName = faker.name().firstName();
                    String lastName = faker.name().lastName();
                    String email = faker.internet().emailAddress();
                    String phoneNumber = faker.phoneNumber().cellPhone();
                    if (emails.contains(email) || phoneNumbers.contains(phoneNumber)) {
                        i--;
                        continue;
                    }

                    Member fakeMember = new Member(firstName, lastName, email, phoneNumber);
                    this.memberRepository.saveAndFlush(fakeMember);

                }
            }
        }
    }

}
