package ru.cotarius.hibernatecourse.library.controllers.bookControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/books")
public class UiBookController {
    private BookService bookService;

    @Autowired
    public UiBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public String findAll(Model model){
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

}
