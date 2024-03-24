package ru.cotarius.hibernatecourse.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cotarius.hibernatecourse.library.entity.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
