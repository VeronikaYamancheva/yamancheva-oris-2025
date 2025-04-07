package ru.itis.vhsroni.exceptions;

public class EmptyConfirmationCodeValidationServiceException extends ValidationServiceException{

    public EmptyConfirmationCodeValidationServiceException() {
        super("Код подтверждения не предоставлен");
    }
}
