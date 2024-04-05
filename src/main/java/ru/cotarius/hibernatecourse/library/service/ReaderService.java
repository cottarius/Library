package ru.cotarius.hibernatecourse.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cotarius.hibernatecourse.library.controllers.dto.ReaderRequest;
import ru.cotarius.hibernatecourse.library.entity.Reader;
import ru.cotarius.hibernatecourse.library.repository.ReaderRepository;

import java.util.List;

@Service
@Slf4j
public class ReaderService {
    private ReaderRepository repository;

    @Autowired
    public ReaderService(ReaderRepository repository) {
        this.repository = repository;
    }

    public Reader create(ReaderRequest readerRequest){
        Reader reader = new Reader(readerRequest.getFirstName(), readerRequest.getLastName());
        return repository.save(reader);
    }
    public List<Reader> findAll(){
        return repository.findAll();
    }

    public Reader getById(long id){
        Reader reader = repository.findById(id).orElse(null);
        isReaderNull(reader, id);
        return reader;
    }
    public void delete(long id){
        Reader reader = repository.findById(id).orElse(null);
        isReaderNull(reader, id);
        repository.delete(reader);
    }

    private static void isReaderNull(Reader reader, long id) {
        if (reader == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "reader with id=" + id + " not found");
        }
    }
}
