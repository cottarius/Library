package ru.cotarius.hibernatecourse.library.controllers.bookControllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.service.BookService;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class BookControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    BookService bookService;

    @Test
    void delete() {
        String bookName = "Война и Мир";
        bookService.createBook(bookName);
        long id = bookService.findAll().stream().filter(b -> b.getTitle().equals(bookName)).findFirst().get().getId();
//        bookService.delete(id);

        Book deleteBook = webTestClient.delete()
                .uri("books/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        assertNull(deleteBook);
    }

    @Test
    void testSave() {
        String name = "Война и Мир";

        Book savedBook = webTestClient.post()
                .uri("/books")
                .bodyValue(name)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        assertEquals(name, savedBook.getTitle());

    }

    @Test
    void testGetById() {
        String name = "test";
        bookService.createBook(name);
        long id = bookService.findByTitle(name).getId();
        Book findedBook = webTestClient.get()
                .uri("books/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(findedBook);
        assertEquals(id, findedBook.getId());
        assertEquals(name, findedBook.getTitle());
    }

    @Test
    void testFindAll() {
        bookService.createBook("Властелин Колец");
        bookService.createBook("Хоббит");
        List<Book> bookList = bookService.findAll();

        List<Book> responseBody = webTestClient.get()
                .uri("books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Book>>() {
                })
                .returnResult()
                .getResponseBody();

        assertEquals(bookList.size(), responseBody.size());
        for (Book book : responseBody) {
            boolean found = bookList
                    .stream()
                    .filter(it -> Objects.equals(book.getId(), it.getId()))
                    .anyMatch(it -> Objects.equals(book.getTitle(), it.getTitle()));

            assertTrue(found);
        }
    }
}