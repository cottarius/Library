package ru.cotarius.hibernatecourse.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reader")
@Data
@NoArgsConstructor
public class Reader {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

//    @OneToOne //(mappedBy = "reader")
//    @JoinColumn(name = "id", referencedColumnName = "reader_id")
//    private Issue issue;

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "Id: " + id + " Reader: " + firstName + " " + lastName;
    }
}
