package ru.itis.vhsroni.exceptions;

public class DentistNotFoundException extends NotFoundServiceException {

    public DentistNotFoundException() {
        super("Dentist not found");
    }
}
