package ru.itis.vhsroni.exceptions;

public class EmailIsAlreadyConfirmedServiceException extends ServiceException{

    public EmailIsAlreadyConfirmedServiceException() {
        super(400, "Email уже подтвержден");
    }
}
