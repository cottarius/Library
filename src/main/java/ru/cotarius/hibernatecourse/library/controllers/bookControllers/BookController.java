package ru.cotarius.hibernatecourse.library.controllers.bookControllers;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "save book to repository")
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody String title) {
        log.info("Поступил запрос на создание книги: " + title);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(title));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "get by id")
    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getById(id));
    }

    @Operation(summary = "get all books")
    @GetMapping
    public ResponseEntity<List<Book>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findAll());
    }
}
