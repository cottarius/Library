package ru.cotarius.hibernatecourse.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "issue")
@Data
@NoArgsConstructor
public class Issue {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "reader_id")//, insertable = false, updatable = false)
    private long readerId;

    @Column(name = "book_id")//, insertable = false, updatable = false)
    private long bookId;

    @Column(name = "issued_at")
    private LocalDate issuedAt;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    @OneToOne(mappedBy = "issue")
//    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @OneToOne(mappedBy = "issue")
//    @JoinColumn(name = "reader_id", referencedColumnName = "id")
    private Reader reader;

    public Issue(long readerId, long bookId) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.issuedAt = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", readerId=" + readerId +
                ", bookId=" + bookId +
                ", issuedAt=" + issuedAt +
                ", returnedAt=" + returnedAt +
                '}';
    }
}
