package ru.cotarius.hibernatecourse.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cotarius.hibernatecourse.library.entity.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
}
