package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import ru.itis.vhsroni.controllers.GreetingController;
import ru.itis.vhsroni.dto.response.GreetingResponse;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GreetingControllerTest {

    public static final String EXPECTED_MESSAGE = "Expected message";

    @Mock
    private MessageSource messageSource;

    private GreetingController greetingController;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        greetingController = new GreetingController(messageSource);
    }

    @Test
    void testGetGreeting() {
        Locale locale = Locale.ENGLISH;
        when(messageSource.getMessage("greeting.message", null, locale)).thenReturn(EXPECTED_MESSAGE);
        ResponseEntity<GreetingResponse> response = greetingController.getGreeting(locale);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(EXPECTED_MESSAGE);
        verify(messageSource, times(1)).getMessage("greeting.message", null, locale);
    }
}

