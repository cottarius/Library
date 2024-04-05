package ru.cotarius.hibernatecourse.library.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cotarius.hibernatecourse.library.controllers.dto.IssueRequest;
import ru.cotarius.hibernatecourse.library.customexceptions.BookHasBeenReturnedException;
import ru.cotarius.hibernatecourse.library.customexceptions.MoreThanAllowedBooksException;
import ru.cotarius.hibernatecourse.library.entity.Book;
import ru.cotarius.hibernatecourse.library.entity.Issue;
import ru.cotarius.hibernatecourse.library.entity.Reader;
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
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    @Setter
    @Value("${spring.application.issue.max_allowed-books:3}")
    private int MAX_ALLOWED_BOOKS;

    @Autowired
    public IssueService(IssueRepository issueRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

//    public List<Book> getAllBooksByReaderId(long id){
//
//    }
    public void delete(long id){
        Issue issue = issueRepository.findById(id).orElse(null);
        if (issue == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        issueRepository.delete(issue);
    }

    public Issue returnBook(long id) {
        if (issueRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (issueRepository.getReferenceById(id).getReturnedAt() != null) {
            throw new BookHasBeenReturnedException("Книга с id:" +
                    issueRepository.getReferenceById(id).getBook().getId() +
                    " уже была возвращена");
        }

        Issue returnedIssue = issueRepository.getReferenceById(id);
        returnedIssue.setReturnedAt(LocalDate.now());
        issueRepository.save(returnedIssue);
        return returnedIssue;
    }

    /**
     * Создание новой записи выдачи книги.
     * @param issueRequest запрос Id пользователя
     * @return новая запись выдачи книг
     */
    public Issue create(IssueRequest issueRequest) {
        Reader reader = readerRepository.findById(issueRequest.getReaderId()).orElse(null);
        Book book = bookRepository.findById(issueRequest.getBookId()).orElse(null);
        if (book == null) {
            log.info("Не удалось найти книгу: " + issueRequest.getBookId());
            throw new NoSuchElementException("Не удалось найти книгу: " + issueRequest.getBookId());
        }
        if (reader == null) {
            log.info("Не удалось найти читателя: " + issueRequest.getReaderId());
            throw new NoSuchElementException("Не удалось найти читателя: " + issueRequest.getReaderId());
        }
        if (!checkMaxAllowedBooks(issueRequest.getReaderId())) {
            log.info("У читателя с id={} превышено допустимое значение хранения книг", issueRequest.getReaderId());
            throw new MoreThanAllowedBooksException("Превышено допустимое количество книг, разрешённых к выдаче");
        }

        Issue issue = new Issue(reader, book);
        return issueRepository.save(issue);
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Issue findById(long id) {
        Issue issue = issueRepository.findById(id).orElse(null);
        ifIssueNull(issue, id);
        return issueRepository.getReferenceById(id);
    }

    private static void ifIssueNull(Issue issue, long id) {
        if (issue == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found issue with id = " + id);
        }
    }

    /**
     * Проверка допустимого количества выданных книг пользователю
     * @param id id пользователя
     * @return
     */
    public boolean checkMaxAllowedBooks(long id) {
        int countOfBooks = 1;
        for (Issue issue : issueRepository.findAll()) {
            if (issue.getReader().getId() == id) {
                countOfBooks++;
                if (countOfBooks > MAX_ALLOWED_BOOKS) {
                    return false;
                }
            }
        }
        return true;
    }
}
