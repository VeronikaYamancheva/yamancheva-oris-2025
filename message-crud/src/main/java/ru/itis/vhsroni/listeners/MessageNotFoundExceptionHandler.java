package ru.itis.vhsroni.listeners;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.vhsroni.exceptions.MessageNotFoundException;

@RestControllerAdvice
public class MessageNotFoundExceptionHandler {

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleMessageNotFoundException(MessageNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
