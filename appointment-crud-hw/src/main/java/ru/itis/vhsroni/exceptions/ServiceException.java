package ru.itis.vhsroni.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private final int errorStatus;

    private final String errorMessage;
}
