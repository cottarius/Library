package ru.cotarius.hibernatecourse.library.controllers.issueControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cotarius.hibernatecourse.library.controllers.dto.IssueRequest;
import ru.cotarius.hibernatecourse.library.customexceptions.BookHasBeenReturnedException;
import ru.cotarius.hibernatecourse.library.entity.Issue;
import ru.cotarius.hibernatecourse.library.service.IssueService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/issues")
public class IssueController {
    private IssueService issueService;

    @PutMapping("{id}")
    public ResponseEntity<Issue> returnIssue(@PathVariable long id){
        log.info("Поступил запрос на возврат книги в библиотеку");

        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.returnIssue(id));
        } catch (BookHasBeenReturnedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/create")
    public ResponseEntity<Issue> create(@RequestBody IssueRequest issueRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(issueService.create(issueRequest));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/all2")
    public List<Issue> findAll(){
        List<Issue> issues = issueService.findAll();
        return issues;
    }
}
