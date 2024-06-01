package ru.cotarius.hibernatecourse.library.controllers.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cotarius.hibernatecourse.library.kafka.KafkaProducer;
import ru.cotarius.hibernatecourse.library.service.BookService;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    private final BookService bookService;

    @PostMapping("/kafka/send")
    public String sendMessage(@RequestBody String message) {
        kafkaProducer.sendMessage(message);
        return "Message sent successfully";
    }

    @GetMapping("/kafka/sendBook/{id}")
    public String sendBook(@PathVariable Long id) {
        var book = bookService.getById(id);
        kafkaProducer.sendMessage(book.getTitle());
        return "Message sent successfully";
    }
}
