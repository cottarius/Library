package ru.cotarius.hibernatecourse.library.controllers.issueControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.entity.Issue;
import ru.cotarius.hibernatecourse.library.entity.Reader;
import ru.cotarius.hibernatecourse.library.service.IssueService;

import java.util.ArrayList;
import java.util.List;

//@Controller
@RequestMapping("/issues")
@Slf4j
public class UiIssueController {
    private IssueService issueService;

    @Autowired
    public UiIssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/all")
    public String findAll(Model model){
        List<Issue> issues = issueService.findAll();
        model.addAttribute("issues", issues);
        return "issues";
    }

    @GetMapping("/reader/{id}")
    public String getAllBooksFromReaderByReaderId(@PathVariable Long id, Model model){
        List<Book> books = new ArrayList<>();
        Reader reader = new Reader();
        List<Issue> issues = issueService.findAll();
        for(Issue issue: issues){
            if(issue.getReader().getId() == id){
                reader = issue.getReader();
                books.add(issue.getBook());
            }
        }
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);
        return "books-from-reader";
    }
}
