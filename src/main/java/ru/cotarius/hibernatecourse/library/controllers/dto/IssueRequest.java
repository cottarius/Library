package ru.cotarius.hibernatecourse.library.controllers.dto;

import lombok.Data;

@Data
public class IssueRequest {
    private long readerId;
    private long bookId;

}
