package ru.itis.vhsroni.exceptions;

public class AppointmentNotFoundException extends NotFoundServiceException{

    public AppointmentNotFoundException() {
        super("Appointment not found");
    }
}
