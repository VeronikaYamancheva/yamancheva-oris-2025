package ru.itis.vhsroni.exceptions;

public class InvalidTokenServiceException extends ServiceException{

    public InvalidTokenServiceException() {
        super(400, "Предоставлен недействительный токен");
    }
}
