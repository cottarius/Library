package ru.cotarius.hibernatecourse.library.controllers.issueControllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cotarius.hibernatecourse.library.controllers.dto.IssueRequest;
import ru.cotarius.hibernatecourse.library.customexceptions.BookHasBeenReturnedException;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.entity.Issue;
import ru.cotarius.hibernatecourse.library.entity.Reader;
import ru.cotarius.hibernatecourse.library.service.IssueService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @Operation(summary = "return Book to the library")
    @PutMapping("{id}")
    public ResponseEntity<Issue> returnIssue(@PathVariable long id){
        log.info("Поступил запрос на возврат книги в библиотеку");

        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.returnBook(id));
        } catch (BookHasBeenReturnedException e){
            log.info(e.toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "create new issue")
    @PostMapping
    public ResponseEntity<Issue> create(@RequestBody IssueRequest issueRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(issueService.create(issueRequest));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "get all issues")
    @GetMapping
    public ResponseEntity<List<Issue>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.findAll());
    }

    @Operation(summary = "get by id")
    @GetMapping("{id}")
    public ResponseEntity<Issue> getById(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.findById(id));
    }

    @Operation(summary = "delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        issueService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("reader/{id}")
    public ResponseEntity<Map<String, List<Book>>> getAllBooksFromReaderByReaderId(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.findBooksByReaderId(id));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
