package ru.cotarius.hibernatecourse.library.controllers.readerControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.cotarius.hibernatecourse.library.entity.Reader;
import ru.cotarius.hibernatecourse.library.repository.ReaderRepository;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ReaderControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReaderRepository readerRepository;

    @BeforeEach
    void setUp() {
        readerRepository.deleteAll();
    }

    @Test
    void delete() {
        Reader reader = new Reader("Ivan", "Petrov");

        Reader savedReader = readerRepository.save(reader);

        webTestClient.delete()
                .uri("/readers/{id}", Collections.singletonMap("id", savedReader.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);

    }

    @Test
    void getById() {
        Reader reader = new Reader("Ivan", "Petrov");
        Reader savedReader = readerRepository.save(reader);
        webTestClient.get()
                .uri("/readers/{id}", Collections.singletonMap("id", savedReader.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedReader.getId())
                .jsonPath("$.firstName").isEqualTo(savedReader.getFirstName())
                .jsonPath("$.lastName").isEqualTo(savedReader.getLastName());
    }

    @Test
    void findAll() {
        Reader reader1 = new Reader("Ivan", "Petrov");
        Reader reader2 = new Reader("Anna", "Ivanova");
        readerRepository.saveAll(List.of(reader1, reader2));

        webTestClient.get()
                .uri("/readers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Reader.class)
                .consumeWith(System.out::println);
    }

    @Test
    void testCreateReader() {
        String firstName = "Ivan";
        String lastName = "Petrov";
        Reader reader = new Reader(firstName, lastName);

        webTestClient.post()
                .uri("/readers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reader)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo("Ivan")
                .jsonPath("$.lastName").isEqualTo("Petrov");
    }
}