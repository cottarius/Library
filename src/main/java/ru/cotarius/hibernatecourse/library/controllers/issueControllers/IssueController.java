package ru.cotarius.hibernatecourse.library.controllers.issueControllers;

import io.micrometer.core.instrument.Metrics;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import ru.cotarius.hibernatecourse.library.controllers.dto.IssueRequest;
import ru.cotarius.hibernatecourse.library.customexceptions.BookHasBeenReturnedException;
import ru.cotarius.hibernatecourse.library.customexceptions.MoreThanAllowedBooksException;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.entity.Issue;
import ru.cotarius.hibernatecourse.library.service.IssueService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;
    private final AtomicInteger showIssuesCounter = Metrics.gauge("all issues", new AtomicInteger());
    private final AtomicInteger showFailureIssuesCounter = Metrics.gauge("all failure issues", new AtomicInteger());

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @Scheduled(fixedDelay = 2000, initialDelay = 0)
    public void schedulingTask() {
        showIssuesCounter.get();
    }

    @Operation(summary = "return Book to the library")
    @PutMapping("{id}")
    public ResponseEntity<Issue> returnIssue(@PathVariable long id){
        log.info("Поступил запрос на возврат книги в библиотеку");

        try {
            issueService.returnBook(id);
            if(showIssuesCounter.get() > 0) {
                showIssuesCounter.decrementAndGet();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
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
            issueService.create(issueRequest);
            showIssuesCounter.incrementAndGet();
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (MoreThanAllowedBooksException e){
            showFailureIssuesCounter.incrementAndGet();
            return ResponseEntity.internalServerError().build();
        } catch (NoSuchElementException e) {
            showFailureIssuesCounter.incrementAndGet();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        try {
            issueService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e){
            showFailureIssuesCounter.incrementAndGet();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @Operation(summary = "delete by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        try {
            issueService.delete(id);
            if(showIssuesCounter.get() > 0) {
                showIssuesCounter.decrementAndGet();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            showFailureIssuesCounter.incrementAndGet();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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
