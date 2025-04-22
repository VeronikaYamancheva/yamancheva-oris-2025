package ru.itis.vhsroni.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.itis.vhsroni.dto.exception.ExceptionDto;
import ru.itis.vhsroni.exceptions.*;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

    private static final String TEST_REQUEST_URI = "/api/test";

    private static final String TEST_ALTERNATE_URL = "/api/some/endpoint";

    private static final String CONTENT_TYPE_HEADER = "application/json;charset=UTF-8";

    private static final int BAD_REQUEST_STATUS = 400;

    private static final int NOT_FOUND_STATUS = 404;

    private static final int INTERNAL_ERROR_STATUS = 500;

    private static final String BAD_REQUEST_MESSAGE = "Bad request";

    private static final String NOT_FOUND_MESSAGE = "Not found";

    private static final String DENTIST_NOT_FOUND_MESSAGE = "Dentist not found";

    private static final String CLIENT_NOT_FOUND_MESSAGE = "Client not found";

    private static final String APPOINTMENT_NOT_FOUND_MESSAGE = "Appointment not found";

    private static final String SERVER_ERROR_MESSAGE = "Server error";
    
    private static final String TEST_MESSAGE = "Test";

    private CustomExceptionHandler exceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void beforeEach() {
        exceptionHandler = new CustomExceptionHandler();
        when(request.getRequestURI()).thenReturn(TEST_REQUEST_URI);
    }

    @Test
    void testHandleServiceExceptionWithServiceException() {
        ServiceException exception = new ServiceException(BAD_REQUEST_STATUS, BAD_REQUEST_MESSAGE);
        ResponseEntity<ExceptionDto> responseEntity = exceptionHandler.handleServiceException(
                exception, request, response);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        ExceptionDto body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(BAD_REQUEST_STATUS, body.getErrorCode());
        assertEquals(BAD_REQUEST_MESSAGE, body.getErrorMessage());
        assertEquals(TEST_REQUEST_URI, body.getErrorUrl());
        assertNotNull(body.getErrorTimestamp());
        verify(response).setContentType(CONTENT_TYPE_HEADER);
    }

    @Test
    void testHandleServiceExceptionWithNotFoundServiceException() {
        NotFoundServiceException exception = new NotFoundServiceException(NOT_FOUND_MESSAGE);
        ResponseEntity<ExceptionDto> responseEntity = exceptionHandler.handleServiceException(
                exception, request, response);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(NOT_FOUND_STATUS, responseEntity.getBody().getErrorCode());
        assertEquals(NOT_FOUND_MESSAGE, responseEntity.getBody().getErrorMessage());
    }

    @Test
    void testHandleServiceExceptionWithDentistNotFoundException() {
        DentistNotFoundException exception = new DentistNotFoundException();
        ResponseEntity<ExceptionDto> responseEntity = exceptionHandler.handleServiceException(
                exception, request, response);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(NOT_FOUND_STATUS, responseEntity.getBody().getErrorCode());
        assertEquals(DENTIST_NOT_FOUND_MESSAGE, responseEntity.getBody().getErrorMessage());
    }

    @Test
    void testHandleServiceExceptionWithClientNotFoundException() {
        ClientNotFoundException exception = new ClientNotFoundException();
        ResponseEntity<ExceptionDto> responseEntity = exceptionHandler.handleServiceException(
                exception, request, response);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(NOT_FOUND_STATUS, responseEntity.getBody().getErrorCode());
        assertEquals(CLIENT_NOT_FOUND_MESSAGE, responseEntity.getBody().getErrorMessage());
    }

    @Test
    void testHandleServiceExceptionWithAppointmentNotFoundException() {
        AppointmentNotFoundException exception = new AppointmentNotFoundException();
        ResponseEntity<ExceptionDto> responseEntity = exceptionHandler.handleServiceException(
                exception, request, response);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(NOT_FOUND_STATUS, responseEntity.getBody().getErrorCode());
        assertEquals(APPOINTMENT_NOT_FOUND_MESSAGE, responseEntity.getBody().getErrorMessage());
    }

    @Test
    void testHandleServiceExceptionVerifiesTimestamp() {
        ServiceException exception = new ServiceException(INTERNAL_ERROR_STATUS, SERVER_ERROR_MESSAGE);
        Instant before = Instant.now();
        ResponseEntity<ExceptionDto> responseEntity = exceptionHandler.handleServiceException(
                exception, request, response);
        Instant after = Instant.now();
        Instant timestamp = Instant.parse(responseEntity.getBody().getErrorTimestamp());
        assertTrue(timestamp.isAfter(before) || timestamp.equals(before));
        assertTrue(timestamp.isBefore(after) || timestamp.equals(after));
    }

    @Test
    void testHandleServiceExceptionVerifiesErrorUrl() {
        when(request.getRequestURI()).thenReturn(TEST_ALTERNATE_URL);
        ServiceException exception = new ServiceException(BAD_REQUEST_STATUS, TEST_MESSAGE);
        ResponseEntity<ExceptionDto> responseEntity = exceptionHandler.handleServiceException(
                exception, request, response);
        assertEquals(TEST_ALTERNATE_URL, responseEntity.getBody().getErrorUrl());
        verify(request).getRequestURI();
    }
}