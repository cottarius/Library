package ru.cotarius.hibernatecourse.library.controllers.bookControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.service.BookService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> create(@RequestBody String title){
        log.info("Поступил запрос на создание книги: " + title);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(title));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
