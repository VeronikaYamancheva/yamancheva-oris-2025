package ru.itis.vhsroni.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.dto.response.GreetingResponse;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final MessageSource messageSource;

    @GetMapping(path = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GreetingResponse> getGreeting(Locale locale) {
        return ResponseEntity.ok(new GreetingResponse(messageSource.getMessage("greeting.message", null, locale)));
    }
}
