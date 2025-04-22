package ru.itis.vhsroni.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itis.vhsroni.dto.request.AppointmentCreationRequest;
import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.dto.response.AppointmentResponse;
import ru.itis.vhsroni.services.AdminAppointmentManagementService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAppointmentsManagementControllerTest {

    private static final String TEST_CLIENT_EMAIL = "client@example.com";

    private static final String TEST_DENTIST_EMAIL = "dentist@example.com";

    private static final String SUCCESSFUL_UPDATE_MESSAGE = "Successful update";

    private static final String UNSUCCESSFUL_UPDATE_MESSAGE = "Unsuccessful update";

    private static final String DELETION_SUCCESS_MESSAGE = "Appointment was deleted";

    private static final int TEST_HOUR = 14;

    private static final int TEST_MINUTE = 30;

    private static final int DAYS_TO_ADD = 1;

    private static final int DATE_RANGE_DAYS = 7;

    @Mock
    private AdminAppointmentManagementService adminAppointmentManagementService;

    @InjectMocks
    private AdminAppointmentsManagementController controller;

    private UUID testAppointmentId;
    private LocalDate testDate;
    private LocalTime testTime;
    private AppointmentResponse testAppointmentResponse;
    private AppointmentDetailedResponse testDetailedResponse;

    @BeforeEach
    void beforeEach() {
        testAppointmentId = UUID.randomUUID();
        testDate = LocalDate.now().plusDays(DAYS_TO_ADD);
        testTime = LocalTime.of(TEST_HOUR, TEST_MINUTE);
        testAppointmentResponse = new AppointmentResponse();
        testAppointmentResponse.setDate(testDate);
        testAppointmentResponse.setTime(testTime);
        testAppointmentResponse.setClientId(UUID.randomUUID());
        testAppointmentResponse.setDentistId(UUID.randomUUID());
        testDetailedResponse = new AppointmentDetailedResponse();
        testDetailedResponse.setAppointmentId(testAppointmentId);
        testDetailedResponse.setDate(testDate);
        testDetailedResponse.setTime(testTime);
        testDetailedResponse.setClientEmail(TEST_CLIENT_EMAIL);
        testDetailedResponse.setDentistEmail(TEST_DENTIST_EMAIL);
    }

    @Test
    void testGetAllAppointmentsSuccess() {
        List<AppointmentResponse> expected = Arrays.asList(testAppointmentResponse);
        when(adminAppointmentManagementService.findAllAppointments()).thenReturn(expected);
        List<AppointmentResponse> result = controller.getAllAppointments();
        assertEquals(expected, result);
        verify(adminAppointmentManagementService).findAllAppointments();
    }

    @Test
    void testGetAppointmentsByClientEmailSuccess() {
        List<AppointmentResponse> expected = Arrays.asList(testAppointmentResponse);
        when(adminAppointmentManagementService.findAppointmentByClientEmail(TEST_CLIENT_EMAIL)).thenReturn(expected);
        List<AppointmentResponse> result = controller.getAppointmentsByClientEmail(TEST_CLIENT_EMAIL);
        assertEquals(expected, result);
        verify(adminAppointmentManagementService).findAppointmentByClientEmail(TEST_CLIENT_EMAIL);
    }

    @Test
    void testGetAppointmentDetailedInfoByIdSuccess() {
        when(adminAppointmentManagementService.findDetailedInfoByAppointmentId(testAppointmentId)).thenReturn(testDetailedResponse);
        AppointmentDetailedResponse result = controller.getAppointmentDetailedInfoById(testAppointmentId);
        assertEquals(testDetailedResponse, result);
        verify(adminAppointmentManagementService).findDetailedInfoByAppointmentId(testAppointmentId);
    }

    @Test
    void testGetAppointmentsByDateRangeSuccess() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(DATE_RANGE_DAYS);
        List<AppointmentDetailedResponse> expected = Arrays.asList(testDetailedResponse);
        when(adminAppointmentManagementService.findAppointmentsDetailedInfoByDateRange(startDate, endDate)).thenReturn(expected);
        List<AppointmentDetailedResponse> result = controller.getAppointmentsByDateRange(startDate, endDate);
        assertEquals(expected, result);
        verify(adminAppointmentManagementService).findAppointmentsDetailedInfoByDateRange(startDate, endDate);
    }

    @Test
    void testCreateNewAppointmentSuccess() {
        AppointmentCreationRequest request = new AppointmentCreationRequest(TEST_CLIENT_EMAIL, TEST_DENTIST_EMAIL, testDate, testTime);
        when(adminAppointmentManagementService.createNewAppointmentByClientAndDentistsEmails(
                TEST_CLIENT_EMAIL, TEST_DENTIST_EMAIL, testDate, testTime))
                .thenReturn(testAppointmentId);
        UUID result = controller.createNewAppointment(request);
        assertEquals(testAppointmentId, result);
        verify(adminAppointmentManagementService).createNewAppointmentByClientAndDentistsEmails(
                TEST_CLIENT_EMAIL, TEST_DENTIST_EMAIL, testDate, testTime);
    }

    @Test
    void testUpdateAppointmentDateTimeSuccess() {
        when(adminAppointmentManagementService.updateAppointmentDateTime(testDate, testTime, testAppointmentId)).thenReturn(true);
        String result = controller.updateAppointmentDateTime(testDate, testTime, testAppointmentId);
        assertEquals(SUCCESSFUL_UPDATE_MESSAGE, result);
        verify(adminAppointmentManagementService).updateAppointmentDateTime(testDate, testTime, testAppointmentId);
    }

    @Test
    void testUpdateAppointmentDateTimeFailed() {
        when(adminAppointmentManagementService.updateAppointmentDateTime(testDate, testTime, testAppointmentId)).thenReturn(false);
        String result = controller.updateAppointmentDateTime(testDate, testTime, testAppointmentId);
        assertEquals(UNSUCCESSFUL_UPDATE_MESSAGE, result);
        verify(adminAppointmentManagementService).updateAppointmentDateTime(testDate, testTime, testAppointmentId);
    }

    @Test
    void testDeleteAppointmentByIdSuccess() {
        doNothing().when(adminAppointmentManagementService).deleteAppointmentById(testAppointmentId);
        String result = controller.deleteAppointmentById(testAppointmentId);
        assertEquals(DELETION_SUCCESS_MESSAGE, result);
        verify(adminAppointmentManagementService).deleteAppointmentById(testAppointmentId);
    }
}