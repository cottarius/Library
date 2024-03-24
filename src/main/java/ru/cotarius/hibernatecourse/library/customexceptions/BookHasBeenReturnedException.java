package ru.cotarius.hibernatecourse.library.customexceptions;

public class BookHasBeenReturnedException extends RuntimeException{
    public BookHasBeenReturnedException(String s) {
        super(s);
    }
}
