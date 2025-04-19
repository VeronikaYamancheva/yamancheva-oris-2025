package ru.itis.vhsroni.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private int errorStatus;

    private String errorMessage;
}
