package ru.cotarius.hibernatecourse.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        repository.save(reader);
        return reader;
    }
    public List<Reader> finsAll(){
        return repository.findAll();
    }
    public Reader getById(long id){
        return repository.getReferenceById(id);
    }
    public void delete(long id){
        Reader reader = repository.getReferenceById(id);
        repository.delete(reader);
    }
}
