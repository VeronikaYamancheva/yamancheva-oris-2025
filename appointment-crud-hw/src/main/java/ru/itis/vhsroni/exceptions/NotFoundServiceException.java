package ru.itis.vhsroni.exceptions;

public class NotFoundServiceException extends ServiceException{

    public NotFoundServiceException(String errorMessage) {
        super(404, errorMessage);
    }
}
