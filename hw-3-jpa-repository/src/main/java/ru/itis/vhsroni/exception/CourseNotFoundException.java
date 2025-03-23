package ru.itis.vhsroni.exception;

public class CourseNotFoundException extends IllegalArgumentException{

    public CourseNotFoundException(Long id) {
        super("Course with id=%s not found".formatted(id));
    }
}
