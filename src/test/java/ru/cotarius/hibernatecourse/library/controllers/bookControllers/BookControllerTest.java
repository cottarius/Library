package ru.cotarius.hibernatecourse.library.controllers.bookControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.repository.BookRepository;

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
    BookRepository bookRepository;

    @Test
    void save() {
        webTestClient.post()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

    }

    @Test
    void delete() {
    }

    @BeforeEach
    void setUp() {
        bookRepository.saveAll(List.of(
                new Book("Властелин Колец"),
                new Book("Хоббит")
        ));
    }

    @Test
    void testGetById() {
        Book book = bookRepository.findById(1L).get();
        Book findedBook = webTestClient.get()
                .uri("/books/" + book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(findedBook);
        assertEquals(book.getId(), findedBook.getId());
        assertEquals(book.getTitle(), findedBook.getTitle());
    }

    @Test
    void testFindAll() {
        bookRepository.saveAll(
                List.of(
                        new Book("Властелин колец"),
                        new Book("Хоббит")
                )
        );

        List<Book> bookList = bookRepository.findAll();

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