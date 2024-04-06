package ru.cotarius.hibernatecourse.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "issue")
@Data
@NoArgsConstructor
public class Issue{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)//(mappedBy = "reader")
    @JoinColumn(name = "reader_id")//, referencedColumnName = "id")
//    @JsonIgnore
    private Reader reader;

    @OneToOne(fetch = FetchType.LAZY)//(mappedBy = "book")
    @JoinColumn(name = "book_id")//, referencedColumnName = "id")
//    @JsonIgnore
    private Book book;

    @Column(name = "issued_at")
    private LocalDate issuedAt;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    public Issue(Reader reader, Book book) {
        this.reader = reader;
        this.book = book;
        this.issuedAt = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", readerId=" + reader.getId() +
                ", bookId=" + book.getId() +
                ", issuedAt=" + issuedAt +
                ", returnedAt=" + returnedAt +
                '}';
    }
}
