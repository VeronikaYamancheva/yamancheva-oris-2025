package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.AppointmentRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ClientRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.DentistRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.impl.DentistRepositoryImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DentistRepositoryImplTest {

    private static final Long ID = 1L;

    private static final String FIRST_NAME = "Veronika";

    private static final String LAST_NAME = "Yamancheva";

    private static final String EMAIL = "vhsroni@mail.ru";

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private DentistRowMapper mapper;

    @InjectMocks
    private DentistRepositoryImpl dentistRepository;

    private DentistEntity dentistEntity;


    @BeforeEach
    void beforeEach() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(preparedStatement);
        when(jdbcTemplate.getDataSource()).thenReturn(dataSource);

        dentistEntity = new DentistEntity();
        dentistEntity.setId(ID);
        dentistEntity.setFirstName(FIRST_NAME);
        dentistEntity.setLastName(LAST_NAME);
        dentistEntity.setEmail(EMAIL);
    }

    @Test
    void testGetAllInsideSelectedRangeSuccess() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(DentistRowMapper.class)))
                .thenReturn(List.of(dentistEntity));
        List<DentistEntity> result = dentistRepository.getAllInsideSelectedRange(10, 0);
        assertFalse(result.isEmpty());
        assertEquals(dentistEntity, result.get(0));
    }

    @Test
    void testGetCountSuccess() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class)))
                .thenReturn(5);
        Integer result = dentistRepository.getCount();
        assertEquals(5, result);
    }

    @Test
    void testGetAllSuccess() {
        when(jdbcTemplate.query(anyString(), any(DentistRowMapper.class)))
                .thenReturn(List.of(dentistEntity));
        List<DentistEntity> result = dentistRepository.getAll();
        assertFalse(result.isEmpty());
        assertEquals(dentistEntity, result.get(0));
    }

    @Test
    void testFindByIdSuccess() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(DentistRowMapper.class)))
                .thenReturn(dentistEntity);
        Optional<DentistEntity> result = dentistRepository.findById(ID);
        assertTrue(result.isPresent());
        assertEquals(dentistEntity, result.get());

    }

    @Test
    void testFindByIdNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(DentistRowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));
        Optional<DentistEntity> result = dentistRepository.findById(ID);
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveSuccess() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("id", ID);
            keyHolder.getKeyList().add(keyMap);
            return 1;
        });
        KeyHolder mockKeyHolder = mock(KeyHolder.class);
        when(mockKeyHolder.getKey()).thenReturn(ID);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(DentistRowMapper.class)))
                .thenReturn(dentistEntity);
        Optional<DentistEntity> result = dentistRepository.save(FIRST_NAME, LAST_NAME, EMAIL);
        assertTrue(result.isPresent());
        assertEquals(dentistEntity, result.get());
    }



    @Test
    void testSaveException() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenThrow(new RuntimeException("Database error"));
        DbException exception = assertThrows(DbException.class, () -> {
            dentistRepository.save(FIRST_NAME, LAST_NAME, EMAIL);
        });
        assertEquals("Problems with dentist save.", exception.getMessage());
        assertNotNull(exception.getCause());
    }


    @Test
    void testDeleteByIdSuccess() throws DbException {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class))).thenReturn(1);
        dentistRepository.deleteById(ID);
    }

    @Test
    void testDeleteByIdThrowsDbException() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class)))
                .thenThrow(new RuntimeException("Database error"));
        assertThrows(DbException.class, () -> dentistRepository.deleteById(ID));
    }

    @Test
    void testUpdateSuccess() throws DbException {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("id", ID);
            keyHolder.getKeyList().add(keyMap);
            return 1;
        });
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(DentistRowMapper.class)))
                .thenReturn(dentistEntity);
        Optional<DentistEntity> result = dentistRepository.update(ID, FIRST_NAME, LAST_NAME, EMAIL);
        assertTrue(result.isPresent());
        assertEquals(dentistEntity, result.get());
    }

    @Test
    void testUpdateThrowsDbException() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenThrow(new RuntimeException("Database error"));
        assertThrows(DbException.class, () -> dentistRepository.update(ID, FIRST_NAME, LAST_NAME, EMAIL));
    }
}