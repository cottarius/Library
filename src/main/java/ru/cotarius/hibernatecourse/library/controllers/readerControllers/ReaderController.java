package ru.cotarius.hibernatecourse.library.controllers.readerControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cotarius.hibernatecourse.library.controllers.dto.ReaderRequest;
import ru.cotarius.hibernatecourse.library.entity.Reader;
import ru.cotarius.hibernatecourse.library.service.ReaderService;

@RestController
@Slf4j
@RequestMapping("/readers")
public class ReaderController {
    private ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Reader> create(@RequestBody ReaderRequest readerRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).
                    body(readerService.create(readerRequest));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
