package ru.cotarius.hibernatecourse.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.context.annotation.Bean;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
//@BatchSize(size = 5)
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

//    @OneToOne(mappedBy = "book")
//   // @JoinColumn(name = "id", referencedColumnName = "book_id")
//    private Issue issue;

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Title: " + title;
    }
}
