package repository;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itis.vhsroni.exception.UserNotFoundException;
import ru.itis.vhsroni.model.CourseEntity;
import ru.itis.vhsroni.model.UserEntity;
import ru.itis.vhsroni.repository.impl.UserJpaRepositoryImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserJpaRepositoryImplTest {

    private static final Long ID = 1L;

    private static final String NAME = "Name";

    private static final Set<CourseEntity> COURSES = new HashSet<>();

    private static final String UPDATED_NAME = "Updated name";

    private static final Long USER_COUNT = 5L;

    private static final Long COURSE_ID = 1L;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserJpaRepositoryImpl userRepository;

    private UserEntity user;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        user = UserEntity.builder()
                .id(ID)
                .name(NAME)
                .courses(COURSES)
                .build();
    }

    @Test
    void testSaveCorrect() {
        userRepository.save(user);
        verify(entityManager, times(1)).persist(user);
    }

    @Test
    void testUpdateByIdWithExistingEntity() {
        when(entityManager.find(UserEntity.class, ID)).thenReturn(user);
        UserEntity updated = new UserEntity();
        updated.setName(UPDATED_NAME);
        UserEntity result = userRepository.updateById(updated, ID);
        assertEquals(UPDATED_NAME, result.getName());
    }

    @Test
    void testUpdateByIdIfNotFound() {
        when(entityManager.find(UserEntity.class, ID)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userRepository.updateById(new UserEntity(), ID));
    }

    @Test
    void testDeleteByIdCorrect() {
        when(entityManager.find(UserEntity.class, ID)).thenReturn(user);
        userRepository.deleteById(ID);
        verify(entityManager, times(1)).remove(user);
    }

    @Test
    void testDeleteByIdIfNotFound() {
        when(entityManager.find(UserEntity.class, ID)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userRepository.deleteById(ID));
    }

    @Test
    void testFindAllCorrect() {
        TypedQuery<UserEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(user));
        List<UserEntity> result = userRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByIdCorrect() {
        when(entityManager.find(UserEntity.class, ID)).thenReturn(user);
        Optional<UserEntity> result = userRepository.findById(ID);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindById() {
        when(entityManager.find(UserEntity.class, ID)).thenReturn(null);
        Optional<UserEntity> result = userRepository.findById(ID);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByNameIfEntityExists() {
        TypedQuery<UserEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);
        Optional<UserEntity> result = userRepository.findByName(NAME);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindByNameIfNotFound() {
        TypedQuery<UserEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);
        Optional<UserEntity> result = userRepository.findByName(NAME);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByNameIfMultipleResults() {
        TypedQuery<UserEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NonUniqueResultException.class);
        assertThrows(UserNotFoundException.class, () -> userRepository.findByName(NAME));
    }

    @Test
    void testFindUsersWithNoCourses() {
        TypedQuery<UserEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(user));
        List<UserEntity> result = userRepository.findUsersWithNoCourses();
        assertEquals(1, result.size());
    }

    @Test
    void testCountUsersByCourseId() {
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(USER_COUNT);
        Long count = userRepository.countUsersByCourseId(COURSE_ID);
        assertEquals(USER_COUNT, count);
    }

    @Test
    void testFindUsersByCourseId() {
        TypedQuery<UserEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(user));
        List<UserEntity> result = userRepository.findUsersByCourseId(COURSE_ID);
        assertEquals(1, result.size());
    }
}
