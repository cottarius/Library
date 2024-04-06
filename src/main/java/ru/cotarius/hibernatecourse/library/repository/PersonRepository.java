package ru.cotarius.hibernatecourse.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.cotarius.hibernatecourse.library.entity.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByLogin(String login);
}
