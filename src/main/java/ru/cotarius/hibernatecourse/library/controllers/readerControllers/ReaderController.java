package ru.cotarius.hibernatecourse.library.controllers.readerControllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cotarius.hibernatecourse.library.controllers.dto.ReaderRequest;
import ru.cotarius.hibernatecourse.library.entity.Reader;
import ru.cotarius.hibernatecourse.library.service.ReaderService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/readers")
public class ReaderController {
    private ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete reader by id")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        readerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("{id}")
    @Operation(summary = "get reader by id")
    public ResponseEntity<Reader> getById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getById(id));
    }

    @GetMapping
    @Operation(summary = "get all readers")
    public ResponseEntity<List<Reader>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.findAll());
    }

    @Operation(summary = "save reader to repository")
    @PostMapping
    public ResponseEntity<Reader> create(@RequestBody ReaderRequest readerRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(readerService.create(readerRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
