package ru.itis.vhsroni.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, Model model, HttpServletRequest request) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        model.addAttribute("errors", errors);
        model.addAttribute("errorStatus", 400);
        model.addAttribute("errorMessage", "Ошибка валидации данных");
        model.addAttribute("errorTimestamp", Instant.now().toString());
        model.addAttribute("errorUrl", request.getRequestURL());
        return "error";
    }
}
