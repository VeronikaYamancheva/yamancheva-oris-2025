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
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.AppointmentProcedureRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ProcedureRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.impl.AppointmentProcedureRepositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AppointmentProcedureRepositoryImplTest {

    private static final Long APPOINTMENT_ID = 1L;
    private static final Long PROCEDURE_ID = 2L;
    private static final Long ENTITY_ID = 3L;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private AppointmentProcedureRowMapper mapper;

    @Mock
    private ProcedureRowMapper procedureRowMapper;

    @InjectMocks
    private AppointmentProcedureRepositoryImpl repository;

    private AppointmentProcedureEntity appointmentProcedureEntity;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        appointmentProcedureEntity = new AppointmentProcedureEntity();
    }

    @Test
    void testSaveSuccess() throws DbException {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("id", ENTITY_ID);
            keyHolder.getKeyList().add(keyMap);
            return 1;
        });
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(AppointmentProcedureRowMapper.class)))
                .thenReturn(appointmentProcedureEntity);

        Optional<AppointmentProcedureEntity> result = repository.save(APPOINTMENT_ID, PROCEDURE_ID);
        assertTrue(result.isPresent());
        assertEquals(appointmentProcedureEntity, result.get());
    }

    @Test
    void testSaveThrowsDbException() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenThrow(new RuntimeException("Database error"));
        assertThrows(DbException.class, () -> repository.save(APPOINTMENT_ID, PROCEDURE_ID));
    }

    @Test
    void testFindByIdSuccess() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(AppointmentProcedureRowMapper.class)))
                .thenReturn(appointmentProcedureEntity);
        Optional<AppointmentProcedureEntity> result = repository.findById(ENTITY_ID);
        assertTrue(result.isPresent());
        assertEquals(appointmentProcedureEntity, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(AppointmentProcedureRowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));
        Optional<AppointmentProcedureEntity> result = repository.findById(ENTITY_ID);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindProceduresListByAppointmentId() {
        ProcedureEntity procedure = new ProcedureEntity();
        List<ProcedureEntity> expectedList = List.of(procedure);
        when(jdbcTemplate.query(anyString(), any(Object[].class), eq(procedureRowMapper)))
                .thenReturn(expectedList);

        List<ProcedureEntity> result = repository.findProceduresListByAppointmentId(APPOINTMENT_ID);
        assertEquals(expectedList, result);
    }
}
