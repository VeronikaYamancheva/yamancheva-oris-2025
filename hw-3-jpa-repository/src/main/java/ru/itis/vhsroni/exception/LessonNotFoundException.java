package ru.itis.vhsroni.exception;

public class LessonNotFoundException extends IllegalArgumentException{

    public LessonNotFoundException(Long id) {
        super("Lesson with id=%s not found".formatted(id));
    }
}
