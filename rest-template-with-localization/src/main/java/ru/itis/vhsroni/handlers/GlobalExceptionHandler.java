package ru.itis.vhsroni.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.vhsroni.dto.exceptions.ExceptionDto;
import ru.itis.vhsroni.exceptions.HolidayServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HolidayServiceException.class)
    public ResponseEntity<ExceptionDto> handleHolidayServiceException(HolidayServiceException ex) {
        return ResponseEntity
                .status(ex.getErrorStatus())
                .body(ExceptionDto.builder()
                        .errorCode(ex.getErrorStatus())
                        .message(ex.getErrorMessage())
                        .build());
    }
}
