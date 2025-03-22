package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl.AppointmentServiceImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

    private static final Long APPOINTMENT_ID = 1L;

    private static final Long DENTIST_ID = 1L;

    private static final String DENTIST_FIRST_NAME = "Veronika";

    private static final String DENTIST_LAST_NAME = "Yamancheva";

    private static final String DENTIST_EMAIL = "vhsroni@mail.ru";

    private static final Long PROCEDURE_ID = 1L;

    private static final String PROCEDURE_NAME = "Name";

    private static final String PROCEDURE_DESCRIPTION = "Description";

    private static final Long CLIENT_ID = 1L;

    private static final String CLIENT_FIRST_NAME = "Veronika";

    private static final String CLIENT_LAST_NAME ="Yamancheva";

    private static final String CLIENT_EMAIL = "vhsroni@mail.ru";

    private static final String CLIENT_NICKNAME = "vhsroni";

    private static final String CLIENT_PASSWORD = "password";

    private static final boolean CLIENT_IS_ADMIN = true;

    private static final Date APPOINTMENT_DATE = Date.valueOf("2023-10-01");

    private static final Time APPOINTMENT_TIME = Time.valueOf("10:00:00");

    private static final Long[] APPOINTMENT_PROCEDURES = {1L, 2L};

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private ProcedureRepository procedureRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AppointmentProcedureRepository appointmentProcedureRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDentists() {
        List<DentistEntity> expectedDentists = Arrays.asList(
                new DentistEntity(DENTIST_ID, DENTIST_FIRST_NAME, DENTIST_LAST_NAME, DENTIST_EMAIL)
        );
        when(dentistRepository.getAll()).thenReturn(expectedDentists);
        List<DentistEntity> actualDentists = appointmentService.getAllDentists();
        assertEquals(expectedDentists, actualDentists);
    }

    @Test
    void testGetAllProcedures() {
        List<ProcedureEntity> expectedProcedures = Arrays.asList(
                new ProcedureEntity(PROCEDURE_ID, PROCEDURE_NAME, PROCEDURE_DESCRIPTION)
        );
        when(procedureRepository.getAll()).thenReturn(expectedProcedures);
        List<ProcedureEntity> actualProcedures = appointmentService.getAllProcedures();
        assertEquals(expectedProcedures, actualProcedures);
    }

    @Test
    void testBookAppointmentWhenInputIsValid() throws DbException {
        ClientEntity client = new ClientEntity(CLIENT_ID, CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_EMAIL,
                CLIENT_NICKNAME, CLIENT_PASSWORD, CLIENT_IS_ADMIN, null);
        DentistEntity dentist = new DentistEntity(DENTIST_ID, DENTIST_FIRST_NAME, DENTIST_LAST_NAME, DENTIST_EMAIL);
        AppointmentEntity appointment = new AppointmentEntity(APPOINTMENT_ID, client.getId(), dentist.getId(),
                APPOINTMENT_DATE, APPOINTMENT_TIME);
        when(clientRepository.findByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client));
        when(dentistRepository.findById(DENTIST_ID)).thenReturn(Optional.of(dentist));
        when(appointmentRepository.save(client.getId(), DENTIST_ID, APPOINTMENT_DATE,
                APPOINTMENT_TIME)).thenReturn(Optional.of(appointment));
        Optional<AppointmentEntity> result = appointmentService.bookAppointment(CLIENT_FIRST_NAME, CLIENT_LAST_NAME,
                CLIENT_EMAIL, DENTIST_ID, APPOINTMENT_DATE, APPOINTMENT_TIME, APPOINTMENT_PROCEDURES);
        assertTrue(result.isPresent());
        assertEquals(appointment, result.get());
    }

    @Test
    void testBookAppointmentWhenClientDoesNotExist() {
        when(clientRepository.findByEmail(CLIENT_EMAIL)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.bookAppointment(CLIENT_FIRST_NAME, CLIENT_LAST_NAME,
                        CLIENT_EMAIL, DENTIST_ID, APPOINTMENT_DATE, APPOINTMENT_TIME, APPOINTMENT_PROCEDURES)
        );
    }

    @Test
    void testBookAppointmentWhenDentistDoesNotExist() {
        ClientEntity client = new ClientEntity(CLIENT_ID, CLIENT_FIRST_NAME, CLIENT_LAST_NAME, CLIENT_EMAIL,
                CLIENT_NICKNAME, CLIENT_PASSWORD, CLIENT_IS_ADMIN, null);
        when(clientRepository.findByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client));
        when(dentistRepository.findById(DENTIST_ID)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.bookAppointment(CLIENT_FIRST_NAME, CLIENT_LAST_NAME,
                        CLIENT_EMAIL, DENTIST_ID, APPOINTMENT_DATE, APPOINTMENT_TIME, APPOINTMENT_PROCEDURES));
    }

    @Test
    void testGetAllWithProceduresByClientId() {
        AppointmentEntity appointment = new AppointmentEntity(APPOINTMENT_ID, CLIENT_ID, DENTIST_ID,
                APPOINTMENT_DATE, APPOINTMENT_TIME);
        List<AppointmentEntity> appointments = Arrays.asList(appointment);
        List<ProcedureEntity> procedures = Arrays.asList(
                new ProcedureEntity(PROCEDURE_ID, PROCEDURE_NAME, PROCEDURE_DESCRIPTION));
        when(appointmentRepository.getAllByClientId(CLIENT_ID)).thenReturn(appointments);
        when(appointmentProcedureRepository.findProceduresListByAppointmentId(APPOINTMENT_ID)).thenReturn(procedures);
        List<Map<AppointmentEntity, List<ProcedureEntity>>> result =
                appointmentService.getAllWithProceduresByClientId(CLIENT_ID);
        assertEquals(1, result.size());
        Map<AppointmentEntity, List<ProcedureEntity>> entry = result.get(0);
        assertEquals(appointment, entry.keySet().iterator().next());
        assertEquals(procedures, entry.get(appointment));
    }
}