package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.AppointmentRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.impl.AppointmentRepositoryImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AppointmentRepositoryImplTest {

    private static final Long CLIENT_ID = 1L;
    private static final Long DENTIST_ID = 2L;
    private static final Long APPOINTMENT_ID = 3L;
    private static final Date DATE = Date.valueOf("2025-06-06");
    private static final Time TIME = Time.valueOf("10:00:00");

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private AppointmentRowMapper mapper;

    @InjectMocks
    private AppointmentRepositoryImpl repository;

    private AppointmentEntity appointmentEntity;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        appointmentEntity = new AppointmentEntity(APPOINTMENT_ID, CLIENT_ID, DENTIST_ID, DATE, TIME);
    }

    @Test
    void testSaveSuccess() throws DbException {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("id", APPOINTMENT_ID);
            keyHolder.getKeyList().add(keyMap);
            return 1;
        });
        KeyHolder mockKeyHolder = mock(KeyHolder.class);
        when(mockKeyHolder.getKey()).thenReturn(APPOINTMENT_ID);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(AppointmentRowMapper.class)))
                .thenReturn(appointmentEntity);
        Optional<AppointmentEntity> result = repository.save(CLIENT_ID, DENTIST_ID, DATE, TIME);
        assertTrue(result.isPresent());
        assertEquals(appointmentEntity, result.get());
    }

    @Test
    void testSaveThrowsDbException() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenThrow(new RuntimeException("Database error"));
        assertThrows(DbException.class, () -> repository.save(CLIENT_ID, DENTIST_ID, DATE, TIME));
    }

    @Test
    void testFindByIdSuccess() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(AppointmentRowMapper.class)))
                .thenReturn(appointmentEntity);
        Optional<AppointmentEntity> result = repository.findById(APPOINTMENT_ID);
        assertTrue(result.isPresent());
        assertEquals(appointmentEntity, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(AppointmentRowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));
        Optional<AppointmentEntity> result = repository.findById(APPOINTMENT_ID);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllByClientIdSuccess() {
        List<AppointmentEntity> expectedList = List.of(appointmentEntity);
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(AppointmentRowMapper.class)))
                .thenReturn(expectedList);
        List<AppointmentEntity> result = repository.getAllByClientId(CLIENT_ID);
        assertEquals(expectedList, result);
    }

    @Test
    void testGetAllByClientIdEmpty() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(AppointmentRowMapper.class)))
                .thenReturn(Collections.emptyList());
        List<AppointmentEntity> result = repository.getAllByClientId(CLIENT_ID);
        assertTrue(result.isEmpty());
    }
}
