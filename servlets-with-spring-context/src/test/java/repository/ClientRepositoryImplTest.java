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
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ClientRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.impl.ClientRepositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ClientRepositoryImplTest {

    private static final Long ID = 1L;

    private static final String FIRST_NAME = "Veronika";

    private static final String LAST_NAME ="Yamancheva";

    private static final String EMAIL = "vhsroni@mail.ru";

    private static final String NICKNAME = "vhsroni";

    private static final String PASSWORD = "password";

    private static final boolean IS_ADMIN = true;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ClientRowMapper rowMapper;

    @InjectMocks
    private ClientRepositoryImpl clientRepository;

    private ClientEntity clientEntity;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);

        clientEntity = new ClientEntity();
        clientEntity.setId(ID);
        clientEntity.setEmail(EMAIL);
        clientEntity.setNickname(NICKNAME);
        clientEntity.setPassword(PASSWORD);
        clientEntity.setAdmin(IS_ADMIN);
    }

    @Test
    void testSaveSuccess() throws DbException {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("id", ID);
            keyHolder.getKeyList().add(keyMap);
            return 1;
        });
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenReturn(clientEntity);
        Optional<ClientEntity> result = clientRepository.save(EMAIL, NICKNAME, PASSWORD, IS_ADMIN);
        assertTrue(result.isPresent());
        assertEquals(clientEntity, result.get());
    }

    @Test
    void testSaveThrowsDbException() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(DbException.class, () -> clientRepository.save(EMAIL, NICKNAME, PASSWORD, IS_ADMIN));
    }

    @Test
    void testFindByIdSuccess() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenReturn(clientEntity);
        Optional<ClientEntity> result = clientRepository.findById(ID);
        assertTrue(result.isPresent());
        assertEquals(clientEntity, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenThrow(new EmptyResultDataAccessException(1));
        Optional<ClientEntity> result = clientRepository.findById(ID);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByEmailSuccess() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenReturn(clientEntity);
        Optional<ClientEntity> result = clientRepository.findByEmail(EMAIL);
        assertTrue(result.isPresent());
        assertEquals(clientEntity, result.get());
    }

    @Test
    void testFindByEmailNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenThrow(new EmptyResultDataAccessException(1));
        Optional<ClientEntity> result = clientRepository.findByEmail(EMAIL);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByNicknameSuccess() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenReturn(clientEntity);
        Optional<ClientEntity> result = clientRepository.findByNickname(NICKNAME);

        assertTrue(result.isPresent());
        assertEquals(clientEntity, result.get());
    }

    @Test
    void testFindByNicknameNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenThrow(new EmptyResultDataAccessException(1));
        Optional<ClientEntity> result = clientRepository.findByNickname(NICKNAME);
        assertFalse(result.isPresent());
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
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(ClientRowMapper.class))).thenReturn(clientEntity);
        Optional<ClientEntity> result = clientRepository.update(FIRST_NAME, LAST_NAME, EMAIL);
        assertTrue(result.isPresent());
        assertEquals(clientEntity, result.get());
    }

    @Test
    void testUpdateThrowsDbException() {
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(DbException.class, () -> clientRepository.update(FIRST_NAME, LAST_NAME, EMAIL));
    }

    @Test
    void testGetAllNotAdminsSuccess() {
        when(jdbcTemplate.query(anyString(), any(ClientRowMapper.class))).thenReturn(List.of(clientEntity));
        List<ClientEntity> result = clientRepository.getAllNotAdmins();
        assertFalse(result.isEmpty());
        assertEquals(clientEntity, result.get(0));
    }
}