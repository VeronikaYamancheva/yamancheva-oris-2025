package ru.itis.vhsroni.exceptions;

public class EmailNotFoundServiceException extends NotFoundServiceException{

    public EmailNotFoundServiceException() {
        super("Пользователь с таким email не найден");
    }
}
