package ru.itis.vhsroni.exceptions;

import lombok.Getter;

@Getter
public class ValidationServiceException extends ServiceException {

    public ValidationServiceException(String errorMessage) {
        super(400, errorMessage);
    }
}
