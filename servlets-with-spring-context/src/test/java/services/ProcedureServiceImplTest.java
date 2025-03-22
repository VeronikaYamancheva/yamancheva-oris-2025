package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.ProcedureRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl.ProcedureServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcedureServiceImplTest {

    private static final Long ID = 1L;

    private static final String NAME = "name";

    private static final String DESCRIPTION = "description";

    private static final int PAGE = 1;

    private static final int OFFSET = 0;

    private static final int PAGE_SIZE = 7;

    private static final int PROCEDURE_COUNT = 10;

    @Mock
    private ProcedureRepository procedureRepository;

    @InjectMocks
    private ProcedureServiceImpl procedureService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetServicesList() {
        List<ProcedureEntity> expectedProcedures = Arrays.asList(
                new ProcedureEntity(ID, NAME, DESCRIPTION)
        );
        when(procedureRepository.getAllInsideSelectedRange(PAGE_SIZE, OFFSET)).thenReturn(expectedProcedures);
        List<ProcedureEntity> actualProcedures = procedureService.getServicesList(PAGE);
        assertEquals(expectedProcedures, actualProcedures);
    }

    @Test
    void testGetCount() {
        when(procedureRepository.getCount()).thenReturn(PROCEDURE_COUNT);
        int actualCount = procedureService.getCount();
        assertEquals(PROCEDURE_COUNT, actualCount);
    }

    @Test
    void testSaveCorrect() {
        ProcedureEntity expectedProcedure = new ProcedureEntity(ID, NAME, DESCRIPTION);
        when(procedureRepository.save(NAME, DESCRIPTION)).thenReturn(Optional.of(expectedProcedure));
        Optional<ProcedureEntity> actualProcedure = procedureService.save(NAME, DESCRIPTION);
        assertTrue(actualProcedure.isPresent());
        assertEquals(expectedProcedure, actualProcedure.get());
    }

    @Test
    void testSaveFails() {
        when(procedureRepository.save(NAME, DESCRIPTION)).thenReturn(Optional.empty());
        Optional<ProcedureEntity> actualProcedure = procedureService.save(NAME, DESCRIPTION);
        assertFalse(actualProcedure.isPresent());
    }

    @Test
    void testDeleteByIdWhenRepositoryThrowsException() throws DbException {
        doThrow(new DbException("Delete failed")).when(procedureRepository).deleteById(ID);
        assertThrows(DbException.class, () -> procedureService.deleteById(ID));
    }

    @Test
    void testUpdateCorrect() throws DbException {
        ProcedureEntity expectedProcedure = new ProcedureEntity(ID, NAME, DESCRIPTION);
        when(procedureRepository.update(ID, NAME, DESCRIPTION)).thenReturn(Optional.of(expectedProcedure));
        Optional<ProcedureEntity> actualProcedure = procedureService.update(ID, NAME, DESCRIPTION);
        assertTrue(actualProcedure.isPresent());
        assertEquals(expectedProcedure, actualProcedure.get());
    }

    @Test
    void testUpdateFails() throws DbException {
        when(procedureRepository.update(ID, NAME, DESCRIPTION)).thenReturn(Optional.empty());
        Optional<ProcedureEntity> actualProcedure = procedureService.update(ID, NAME, DESCRIPTION);
        assertFalse(actualProcedure.isPresent());
    }

    @Test
    void testUpdateWhenRepositoryThrowsException() throws DbException {
        when(procedureRepository.update(ID, NAME, DESCRIPTION)).thenThrow(new DbException("Update failed"));
        assertThrows(DbException.class, () -> procedureService.update(ID, NAME, DESCRIPTION));
    }
}