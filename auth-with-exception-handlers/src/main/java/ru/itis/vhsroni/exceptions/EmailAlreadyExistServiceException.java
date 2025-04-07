package ru.itis.vhsroni.exceptions;

public class EmailAlreadyExistServiceException extends ServiceException{

    public EmailAlreadyExistServiceException() {
        super(400, "Email уже занят");
    }
}
