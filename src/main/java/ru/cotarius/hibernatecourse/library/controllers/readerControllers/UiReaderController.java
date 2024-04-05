package ru.cotarius.hibernatecourse.library.controllers.readerControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cotarius.hibernatecourse.library.entity.Reader;
import ru.cotarius.hibernatecourse.library.service.ReaderService;

import java.util.List;

@Controller
@RequestMapping("readers")
public class UiReaderController {
    private ReaderService readerService;

    @Autowired
    public UiReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }
    @GetMapping("/all")
    public String findAll(Model model){
        List<Reader> readers = readerService.findAll();
        model.addAttribute("readers", readers);
        return "readers";
    }
}
