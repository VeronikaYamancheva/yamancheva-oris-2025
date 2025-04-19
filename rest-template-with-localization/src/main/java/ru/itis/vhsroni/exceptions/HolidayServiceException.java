package ru.itis.vhsroni.exceptions;

public class HolidayServiceException extends ServiceException{

    public HolidayServiceException(String errorMessage) {
        super(500, errorMessage);
    }
}
