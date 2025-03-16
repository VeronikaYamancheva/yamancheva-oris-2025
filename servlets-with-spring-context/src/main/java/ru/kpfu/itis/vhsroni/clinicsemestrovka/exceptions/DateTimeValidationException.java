package ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions;

public class DateTimeValidationException extends RuntimeException{

    public DateTimeValidationException(String message) {
        super(message);
    }

    public DateTimeValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
