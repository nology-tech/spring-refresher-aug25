package io.nology.library.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.nology.library.book.entities.Book;
import io.nology.library.genre.GenreRepository;
import io.nology.library.genre.entities.Genre;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookEndToEndTest {
    @LocalServerPort
    private int port;

    private final GenreRepository genreRepo;
    private final BookRepository bookRepository;
    private ArrayList<Genre> genres = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();

    @Autowired
    public BookEndToEndTest(GenreRepository genreRepo, BookRepository bookRepository) {
        this.genreRepo = genreRepo;
        this.bookRepository = bookRepository;
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = this.port;
        this.bookRepository.deleteAll();
        this.genreRepo.deleteAll();

        Genre fantasy = new Genre("Fantasy");
        Genre sciFi = new Genre("Science Fiction");
        Genre horror = new Genre("Horror");
        Genre nonFiction = new Genre("Non Fiction");
        Genre fiction = new Genre("Fiction");
        this.genreRepo.saveAll(Arrays.asList(fantasy, sciFi, horror, nonFiction, fiction));
        this.genres.addAll(Arrays.asList(fantasy, sciFi, horror, nonFiction, fiction));

        //
        // create a book, save it in database, store it in my test class
        Book theRavenTower = new Book("Ann Leckie", "The Raven Tower", fantasy, 2019);
        Book houseOfLeaves = new Book("Mark Z. Danielewski", "House of Leaves", horror, 2000);
        Book demonCopperhead = new Book("Barbara Kingsolver", "Demon Copperhead", nonFiction, 2022);
        this.bookRepository.saveAll(Arrays.asList(theRavenTower, houseOfLeaves, demonCopperhead));
        this.books.addAll(Arrays.asList(theRavenTower, houseOfLeaves, demonCopperhead));

    }

    @Test
    public void getAllBooks_BooksInDB_ReturnsSuccess() {
        // arrange - before all
        given()
                .when() // act
                .get("/books")
                .then() // assert
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(3))
                .body("title", hasItems("The Raven Tower", "House of Leaves", books.get(2).getTitle()))
                .body(matchesJsonSchemaInClasspath("schemas/book-list-schema.json"));
    }

    @Test
    public void getAllBooks_NoBooksInDb_ReturnsEmptyArray() {
        // arrange
        this.bookRepository.deleteAll();
        given()
                .when() // act
                .get("/books")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }

    // findById
    // existing book returns expected result
    @Test
    public void findById_IdInDB_ReturnsBook() {
        System.out.println("*******");
        System.out.println(books.get(0).getId());
        System.out.println("*******");
        Book book = books.get(0);
        given()
                .when().get("/books/" + book.getId())
                .then().statusCode(HttpStatus.OK.value())
                .body("title", equalTo("The Raven Tower"))
                .body("author", equalTo(book.getAuthor()))
                .body("genre", equalTo("Fantasy"))
                .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));
    }

    @Test
    public void findById_IdNotInDB_ReturnsNotFound() {
        Long largeId = 2352352627267172L;
        given()
                .when()
                .get("/books/" + largeId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(equalTo("Could not find book with id " + largeId));
    }

    @Test
    public void findById_InvalidIdInPath_ReturnsBadRequest() {
        given()
                .when()
                .get("/books/sdfsdfa")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    // POST createBook

    @Test
    public void createBook_WhenPassedValidData_Created() {
        // arrange
        // create a DTO
        // could just use a hashmap
        String genreId = this.genres.get(4).getId().toString();
        HashMap<String, String> data = new HashMap<>();
        data.put("title", "Pride and Prejudice");
        data.put("author", "Jane Austen");
        data.put("yearPublished", "1819");
        data.put("genreId", genreId);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/books")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("title", equalTo("Pride and Prejudice"))
                .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));
    }

    @Test
    public void createBook_WhenPassedInvalidData_BadRequest() {
        HashMap<String, String> data = new HashMap<>();
        data.put("title", "Pride and Prejudice");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/books")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(matchesJsonSchemaInClasspath("schemas/validation-failed-schema.json"));

    }
}
