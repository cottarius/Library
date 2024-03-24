package ru.cotarius.hibernatecourse.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cotarius.hibernatecourse.library.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}