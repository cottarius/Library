package ru.cotarius.hibernatecourse.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.repository.BookRepository;

import java.util.List;

@Service
@Slf4j
//@RequiredArgsConstructor
public class BookService {
    private BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book createBook(String name){
        Book book = new Book(name);
        repository.save(book);
        return book;
    }
    public Book getById(long id){
        return repository.getReferenceById(id);
    }

    public List<Book> findAll(){
        return repository.findAll();
    }

    public void delete(long id){
        Book book = repository.findById(id).orElse(null);
        if (book == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no book with id " + id);
        }
        repository.delete(book);
    }
}
