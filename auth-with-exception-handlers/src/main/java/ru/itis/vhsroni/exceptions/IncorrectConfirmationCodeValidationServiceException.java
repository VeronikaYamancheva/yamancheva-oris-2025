package ru.itis.vhsroni.exceptions;

public class IncorrectConfirmationCodeValidationServiceException extends ValidationServiceException{

    public IncorrectConfirmationCodeValidationServiceException() {
        super("Проверочный код некорректен");
    }
}
