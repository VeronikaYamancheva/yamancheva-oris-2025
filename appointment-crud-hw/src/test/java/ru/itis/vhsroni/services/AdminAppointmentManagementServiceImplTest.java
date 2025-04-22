package ru.itis.vhsroni.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.itis.vhsroni.data.entities.*;
import ru.itis.vhsroni.data.mappers.AppointmentMapper;
import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.dto.response.AppointmentResponse;
import ru.itis.vhsroni.exceptions.AppointmentNotFoundException;
import ru.itis.vhsroni.exceptions.ClientNotFoundException;
import ru.itis.vhsroni.exceptions.DentistNotFoundException;
import ru.itis.vhsroni.repositories.*;
import ru.itis.vhsroni.services.impl.AdminAppointmentManagementServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminAppointmentManagementServiceImplTest {

    public static final String CLIENT_EMAIL = "client@example.com";

    public static final String DENTIST_EMAIL = "dentist@example.com";

    public static final LocalDate DATE = LocalDate.now();

    public static final LocalDate DATE_PLUS_ONE_DAY = DATE.plusDays(1);

    public static final LocalTime TIME = LocalTime.NOON;

    public static final UUID APPOINTMENT_ID = UUID.randomUUID();

    @InjectMocks
    private AdminAppointmentManagementServiceImpl service;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentRepositoryCustom appointmentRepositoryCustom;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllAppointmentsSuccess() {
        AppointmentEntity entity = new AppointmentEntity();
        AppointmentResponse response = new AppointmentResponse();
        when(appointmentRepository.findAll()).thenReturn(List.of(entity));
        when(appointmentMapper.toResponse(entity)).thenReturn(response);
        List<AppointmentResponse> result = service.findAllAppointments();
        assertEquals(1, result.size());
        verify(appointmentMapper).toResponse(entity);
    }

    @Test
    void testCreateNewAppointmentByClientAndDentistsEmailsSuccess() {
        ClientEntity client = ClientEntity.builder().build();
        DentistEntity dentist = DentistEntity.builder().build();
        AppointmentEntity saved = AppointmentEntity.builder()
                .appointmentId(APPOINTMENT_ID)
                .build();
        when(clientRepository.findByUser_Email(CLIENT_EMAIL)).thenReturn(Optional.of(client));
        when(dentistRepository.findByUser_Email(DENTIST_EMAIL)).thenReturn(Optional.of(dentist));
        when(appointmentRepository.save(any())).thenReturn(saved);
        UUID result = service.createNewAppointmentByClientAndDentistsEmails(CLIENT_EMAIL, DENTIST_EMAIL, DATE, TIME);
        assertEquals(APPOINTMENT_ID, result);
        verify(appointmentRepository).save(any());
    }

    @Test
    void testCreateNewAppointmentByClientAndDentistsEmailsClientNotFound() {
        when(clientRepository.findByUser_Email(any())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () ->
                service.createNewAppointmentByClientAndDentistsEmails(CLIENT_EMAIL, DENTIST_EMAIL, DATE, TIME));
    }

    @Test
    void testCreateNewAppointmentByClientAndDentistsEmailsDentistNotFound() {
        when(clientRepository.findByUser_Email(any())).thenReturn(Optional.of(ClientEntity.builder().build()));
        when(dentistRepository.findByUser_Email(any())).thenReturn(Optional.empty());
        assertThrows(DentistNotFoundException.class, () ->
                service.createNewAppointmentByClientAndDentistsEmails(CLIENT_EMAIL, DENTIST_EMAIL, DATE, TIME));
    }

    @Test
    void testFindDetailedInfoByAppointmentIdFound() {
        AppointmentEntity entity = new AppointmentEntity();
        AppointmentDetailedResponse response = new AppointmentDetailedResponse();
        when(appointmentRepository.findDetailedInfoById(APPOINTMENT_ID)).thenReturn(Optional.of(entity));
        when(appointmentMapper.toDetailedResponse(entity)).thenReturn(response);
        AppointmentDetailedResponse result = service.findDetailedInfoByAppointmentId(APPOINTMENT_ID);
        assertEquals(response, result);
    }

    @Test
    void testFindDetailedInfoByAppointmentIdNotFound() {
        UUID id = UUID.randomUUID();
        when(appointmentRepository.findDetailedInfoById(id)).thenReturn(Optional.empty());
        assertThrows(AppointmentNotFoundException.class, () -> service.findDetailedInfoByAppointmentId(id));
    }

    @Test
    void testUpdateAppointmentDateTimeSuccess() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(new AppointmentEntity()));
        when(appointmentRepository.updateAppointmentDateTime(APPOINTMENT_ID, DATE, TIME)).thenReturn(1);
        boolean result = service.updateAppointmentDateTime(DATE, TIME, APPOINTMENT_ID);
        assertTrue(result);
    }

    @Test
    void testUpdateAppointmentDateTimeNotFound() {
        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(AppointmentNotFoundException.class, () ->
                service.updateAppointmentDateTime(DATE, TIME, UUID.randomUUID()));
    }

    @Test
    void testDeleteAppointmentByIdInvokesRepository() {
        service.deleteAppointmentById(APPOINTMENT_ID);
        verify(appointmentRepository).deleteByAppointmentId(APPOINTMENT_ID);
    }

    @Test
    void testFindAppointmentByClientEmailSuccess() {
        AppointmentEntity entity = new AppointmentEntity();
        AppointmentResponse response = new AppointmentResponse();
        when(appointmentRepository.findByClient_User_Email(CLIENT_EMAIL)).thenReturn(List.of(entity));
        when(appointmentMapper.toResponse(entity)).thenReturn(response);
        List<AppointmentResponse> result = service.findAppointmentByClientEmail(CLIENT_EMAIL);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAppointmentsDetailedInfoByDateRangeDelegatesToCustomRepo() {
        AppointmentDetailedResponse response = new AppointmentDetailedResponse();
        when(appointmentRepositoryCustom.findAppointmentDetailedInfoByDateRange(DATE, DATE_PLUS_ONE_DAY))
                .thenReturn(List.of(response));
        List<AppointmentDetailedResponse> result = service.findAppointmentsDetailedInfoByDateRange(DATE, DATE_PLUS_ONE_DAY);
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }
}
