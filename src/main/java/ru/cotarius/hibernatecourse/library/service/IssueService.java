package ru.cotarius.hibernatecourse.library.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.cotarius.hibernatecourse.library.controllers.dto.IssueRequest;
import ru.cotarius.hibernatecourse.library.customexceptions.BookHasBeenReturnedException;
import ru.cotarius.hibernatecourse.library.customexceptions.MoreThanAllowedBooksException;
import ru.cotarius.hibernatecourse.library.entity.Issue;
import ru.cotarius.hibernatecourse.library.repository.BookRepository;
import ru.cotarius.hibernatecourse.library.repository.IssueRepository;
import ru.cotarius.hibernatecourse.library.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class IssueService {
    private final IssueRepository issueRepository;
    private BookRepository bookRepository;
    private ReaderRepository readerRepository;

    @Setter
    @Value("${spring.application.issue.max_allowed_books:1}")
    private static int MAX_ALLOWED_BOOKS;

    @Autowired
    public IssueService(IssueRepository issueRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public Issue returnIssue(long id) {
        if (issueRepository.getReferenceById(id).getReturnedAt() == null) {
            Issue returnedIssue = issueRepository.getReferenceById(id);
//            issueRepository.delete(returnedIssue);
            returnedIssue.setReturnedAt(LocalDate.now());
            issueRepository.save(returnedIssue);
            return returnedIssue;
        }
        throw new BookHasBeenReturnedException("Книга с id:" +
                issueRepository.getReferenceById(id).getBookId() +
                " уже была возвращена");

    }

    /**
     * Создание новой записи выдачи книги.
     * @param issueRequest запрос Id пользователя
     * @return новая запись выдачи книг
     */
    public Issue create(IssueRequest issueRequest) {
        if (bookRepository.getReferenceById(issueRequest.getBookId()) == null) {
            log.info("Не удалось найти книгу: " + issueRequest.getBookId());
            throw new NoSuchElementException("Не удалось найти книгу: " + issueRequest.getBookId());
        }
        if (readerRepository.getReferenceById(issueRequest.getReaderId()) == null) {
            log.info("Не удалось найти читателя: " + issueRequest.getReaderId());
            throw new NoSuchElementException("Не удалось найти читателя: " + issueRequest.getReaderId());
        }
        if (!checkMaxAllowedBooks(issueRequest.getReaderId())) {
            log.info("У читателя с id={} превышено допустимое значение хранения книг", issueRequest.getReaderId());
            throw new MoreThanAllowedBooksException("Превышено допустимое количество книг, разрешённых к выдаче");
        }
        Issue issue = new Issue(issueRequest.getReaderId(), issueRequest.getBookId());
        issueRepository.save(issue);
        return issue;
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Issue findById(long id) {
        return issueRepository.getReferenceById(id);
    }

    /**
     * Проверка допустимого количества выданных книг пользователю
     * @param id id пользователя
     * @return
     */
    public boolean checkMaxAllowedBooks(long id) {
        int countOfBooks = 1;
        for (Issue issue : issueRepository.findAll()) {
            if (issue.getReaderId() == id) {
                countOfBooks++;
                if (countOfBooks > MAX_ALLOWED_BOOKS) {
                    return false;
                }
            }
        }
        return true;
    }
}
