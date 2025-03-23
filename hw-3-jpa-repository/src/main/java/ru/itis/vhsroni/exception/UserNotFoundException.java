package ru.itis.vhsroni.exception;

public class UserNotFoundException extends IllegalArgumentException{

    public UserNotFoundException(Long id) {
        super("User with id=%s not found".formatted(id));
    }
}
