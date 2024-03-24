package ru.cotarius.hibernatecourse.library.customexceptions;

public class AllreadyHaveBook extends RuntimeException{
    public AllreadyHaveBook(String s) {
        super(s);
    }
}
