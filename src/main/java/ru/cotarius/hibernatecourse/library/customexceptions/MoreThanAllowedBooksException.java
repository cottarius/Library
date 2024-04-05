package ru.cotarius.hibernatecourse.library.customexceptions;

public class MoreThanAllowedBooksException extends IllegalStateException{
    public MoreThanAllowedBooksException(String message) {
        super(message);
    }
}
