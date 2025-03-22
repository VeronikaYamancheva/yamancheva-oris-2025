package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ProcedureRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.impl.ProcedureRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProcedureRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ProcedureRowMapper mapper;

    @InjectMocks
    private ProcedureRepositoryImpl procedureRepository;

    private ProcedureEntity procedure;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        procedure = new ProcedureEntity();
        procedure.setId(1L);
        procedure.setName("Test Procedure");
        procedure.setDescription("Test Description");
    }

    @Test
    void getAllInsideSelectedRange_ShouldReturnProcedures() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), eq(mapper)))
                .thenReturn(List.of(procedure));

        List<ProcedureEntity> result = procedureRepository.getAllInsideSelectedRange(10, 0);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(procedure, result.get(0));
    }

    @Test
    void getCount_ShouldReturnCorrectCount() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(5);

        Integer count = procedureRepository.getCount();

        assertEquals(5, count);
    }

    @Test
    void getAll_ShouldReturnAllProcedures() {
        when(jdbcTemplate.query(anyString(), eq(mapper))).thenReturn(List.of(procedure));

        List<ProcedureEntity> result = procedureRepository.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void save_ShouldReturnSavedProcedure() throws DbException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        keyHolder.getKeyList().add(Map.of("id", 1L));

        when(jdbcTemplate.update(any(), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder holder = invocation.getArgument(1);
            holder.getKeyList().add(Map.of("id", 1L));
            return 1;
        });
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(mapper)))
                .thenReturn(procedure);

        Optional<ProcedureEntity> result = procedureRepository.save("Test Procedure", "Test Description");

        assertTrue(result.isPresent());
        assertEquals(procedure, result.get());
    }

    @Test
    void findById_ShouldReturnProcedureIfExists() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(mapper)))
                .thenReturn(procedure);

        Optional<ProcedureEntity> result = procedureRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(procedure, result.get());
    }

    @Test
    void findById_ShouldReturnEmptyIfNotExists() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(mapper)))
                .thenThrow(EmptyResultDataAccessException.class);

        Optional<ProcedureEntity> result = procedureRepository.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteById_ShouldExecuteDelete() {
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        assertDoesNotThrow(() -> procedureRepository.deleteById(1L));
    }

    @Test
    void update_ShouldReturnUpdatedProcedure() throws DbException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        keyHolder.getKeyList().add(Map.of("id", 1L));

        when(jdbcTemplate.update(any(), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder holder = invocation.getArgument(1);
            holder.getKeyList().add(Map.of("id", 1L));
            return 1;
        });
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(mapper)))
                .thenReturn(procedure);

        Optional<ProcedureEntity> result = procedureRepository.update(1L, "Updated Name", "Updated Description");

        assertTrue(result.isPresent());
        assertEquals(procedure, result.get());
    }

    @Test
    void save_ShouldThrowDbException_WhenInsertFails() {
        when(jdbcTemplate.update(any(), any(KeyHolder.class))).thenThrow(RuntimeException.class);

        assertThrows(DbException.class, () -> procedureRepository.save("Test Procedure", "Test Description"));
    }


    @Test
    void update_ShouldThrowDbException_WhenUpdateFails() {
        when(jdbcTemplate.update(any(), any(KeyHolder.class))).thenThrow(RuntimeException.class);

        assertThrows(DbException.class, () -> procedureRepository.update(1L, "Updated Name", "Updated Description"));
    }

}
