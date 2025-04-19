package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.DentistRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl.DentistServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DentistServiceImplTest {

    private static final Long ID = 1L;

    private static final String FIRST_NAME = "Veronika";

    private static final String LAST_NAME = "Yamancheva";

    private static final String EMAIL = "vhsroni@mail.ru";

    private static final int PAGE = 1;

    private static final int OFFSET = 0;

    private static final int PAGE_SIZE = 7;

    private static final int DENTISTS_COUNT = 10;

    @Mock
    private DentistRepository dentistRepository;

    @InjectMocks
    private DentistServiceImpl dentistService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetServicesList() {
        List<DentistEntity> expectedDentists = Arrays.asList(
                new DentistEntity(ID, FIRST_NAME, LAST_NAME, EMAIL)
        );
        when(dentistRepository.getAllInsideSelectedRange(PAGE_SIZE, OFFSET)).thenReturn(expectedDentists);
        List<DentistEntity> actualDentists = dentistService.getServicesList(PAGE);
        assertEquals(expectedDentists, actualDentists);
    }

    @Test
    void testGetCount() {
        when(dentistRepository.getCount()).thenReturn(DENTISTS_COUNT);
        int actualCount = dentistService.getCount();
        assertEquals(DENTISTS_COUNT, actualCount);
    }

    @Test
    void testSaveCorrect() throws DbException {
        DentistEntity expectedDentist = new DentistEntity(ID, FIRST_NAME, LAST_NAME, EMAIL);
        when(dentistRepository.save(FIRST_NAME, LAST_NAME, EMAIL)).thenReturn(Optional.of(expectedDentist));
        Optional<DentistEntity> actualDentist = dentistService.save(FIRST_NAME, LAST_NAME, EMAIL);
        assertTrue(actualDentist.isPresent());
        assertEquals(expectedDentist, actualDentist.get());
    }

    @Test
    void testSaveFails() throws DbException {
        when(dentistRepository.save(FIRST_NAME, LAST_NAME, EMAIL)).thenReturn(Optional.empty());
        Optional<DentistEntity> actualDentist = dentistService.save(FIRST_NAME, LAST_NAME, EMAIL);
        assertFalse(actualDentist.isPresent());
    }

    @Test
    void testSaveRepositoryThrowsException() throws DbException {
        when(dentistRepository.save(FIRST_NAME, LAST_NAME, EMAIL)).thenThrow(new DbException("Save failed"));
        assertThrows(DbException.class, () -> dentistService.save(FIRST_NAME, LAST_NAME, EMAIL));
    }

    @Test
    void testDeleteById() throws DbException {
        doNothing().when(dentistRepository).deleteById(ID);
        dentistService.deleteById(ID);
    }

    @Test
    void deleteByIdRepositoryThrowsException() throws DbException {
        doThrow(new DbException("Delete failed")).when(dentistRepository).deleteById(ID);
        assertThrows(DbException.class, () -> dentistService.deleteById(ID));
    }

    @Test
    void testUpdate() throws DbException {
        DentistEntity expectedDentist = new DentistEntity(ID, FIRST_NAME, LAST_NAME, EMAIL);
        when(dentistRepository.update(ID, FIRST_NAME, LAST_NAME, EMAIL)).thenReturn(Optional.of(expectedDentist));
        Optional<DentistEntity> actualDentist = dentistService.update(ID, FIRST_NAME, LAST_NAME, EMAIL);
        assertTrue(actualDentist.isPresent());
        assertEquals(expectedDentist, actualDentist.get());
    }

    @Test
    void testUpdateFails() throws DbException {
        when(dentistRepository.update(ID, FIRST_NAME, LAST_NAME, EMAIL)).thenReturn(Optional.empty());
        Optional<DentistEntity> actualDentist = dentistService.update(ID, FIRST_NAME, LAST_NAME, EMAIL);
        assertFalse(actualDentist.isPresent());
    }

    @Test
    void testUpdateRepositoryThrowsException() throws DbException {
        when(dentistRepository.update(ID, FIRST_NAME, LAST_NAME, EMAIL)).thenThrow(new DbException("Update failed"));
        assertThrows(DbException.class, () -> dentistService.update(ID, FIRST_NAME, LAST_NAME, EMAIL));
    }
}