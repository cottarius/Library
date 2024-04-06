package ru.cotarius.hibernatecourse.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cotarius.hibernatecourse.library.entity.Person;
import ru.cotarius.hibernatecourse.library.repository.PersonRepository;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person findByLogin(String login) {
        return personRepository.findByLogin(login).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Нет такого пользователя с login " + login));
    }

//    @EventListener(ContextRefreshedEvent.class)
//    private void createStartData(){
//        Person person1 = new Person();
//        person1.setLogin("admin");
//        person1.setPassword("admin");
//        person1.setRole("admin");
//
//        Person person2 = new Person();
//        person2.setLogin("user");
//        person2.setPassword("user");
//        person2.setRole("user");
//
//        save(person1);
//        save(person2);
//    }

}
