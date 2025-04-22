package ru.itis.vhsroni.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itis.vhsroni.data.entities.AppointmentEntity;
import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.repositories.impl.AppointmentRepositoryCustomImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentRepositoryCustomImplTest {

    public static final LocalDate START_DATE = LocalDate.of(2025, 4, 1);

    public static final LocalDate END_DATE = LocalDate.of(2025, 4, 30);

    @InjectMocks
    private AppointmentRepositoryCustomImpl repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private CriteriaQuery<AppointmentDetailedResponse> criteriaQuery;

    @Mock
    private Root<AppointmentEntity> root;

    @Mock
    private Predicate predicate;

    @Mock
    private TypedQuery<AppointmentDetailedResponse> typedQuery;

    @SuppressWarnings("rawtypes")
    @Mock
    private Join clientJoin;

    @SuppressWarnings("rawtypes")
    @Mock
    private Join dentistJoin;

    @SuppressWarnings("rawtypes")
    @Mock
    private Join clientUserJoin;

    @SuppressWarnings("rawtypes")
    @Mock
    private Join dentistUserJoin;

    @SuppressWarnings("unchecked")
    private Path<LocalDate> mockDatePath = mock(Path.class);

    @BeforeEach
    void beforeEach() {
        when(root.get("date")).thenReturn((Path) mockDatePath);
    }

    @Test
    void testFindAppointmentDetailedInfoByDateRange() {
        AppointmentDetailedResponse mockResponse = mock(AppointmentDetailedResponse.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(AppointmentDetailedResponse.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(AppointmentEntity.class)).thenReturn(root);
        when(root.join("client")).thenReturn(clientJoin);
        when(clientJoin.join("user")).thenReturn(clientUserJoin);
        when(root.join("dentist")).thenReturn(dentistJoin);
        when(dentistJoin.join("user")).thenReturn(dentistUserJoin);
        when(cb.between(mockDatePath, START_DATE, END_DATE)).thenReturn(predicate);
        when(criteriaQuery.select(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(mockResponse));
        List<AppointmentDetailedResponse> result = repository.findAppointmentDetailedInfoByDateRange(START_DATE, END_DATE);
        assertEquals(1, result.size());
        assertEquals(mockResponse, result.get(0));
        verify(entityManager).getCriteriaBuilder();
        verify(cb).createQuery(AppointmentDetailedResponse.class);
        verify(criteriaQuery).from(AppointmentEntity.class);
        verify(root).join("client");
        verify(root).join("dentist");
        verify(entityManager).createQuery(criteriaQuery);
        verify(typedQuery).getResultList();
    }
}
