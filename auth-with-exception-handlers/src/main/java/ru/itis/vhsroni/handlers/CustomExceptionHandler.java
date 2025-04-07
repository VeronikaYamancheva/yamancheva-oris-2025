package ru.itis.vhsroni.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.vhsroni.api.dto.exception.ExceptionDto;
import ru.itis.vhsroni.exceptions.ServiceException;

import java.time.Instant;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionDto> handleServiceException(
            ServiceException e,
            HttpServletRequest request,
            HttpServletResponse response) {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");

        return ResponseEntity
                .status(e.getErrorStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ExceptionDto.builder()
                        .errorCode(e.getErrorStatus())
                        .errorMessage(e.getErrorMessage())
                        .errorTimestamp(Instant.now().toString())
                        .errorUrl(request.getRequestURI())
                        .build());
    }
}
