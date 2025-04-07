package ru.itis.vhsroni.exceptions;

public class InternalServiceException extends ServiceException{

    public InternalServiceException() {
        super(500, "Внутренняя ошибка сервера");
    }
}
