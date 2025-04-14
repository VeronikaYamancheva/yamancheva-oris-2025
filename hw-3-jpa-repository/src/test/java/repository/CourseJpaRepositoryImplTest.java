package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itis.vhsroni.exception.CourseNotFoundException;
import ru.itis.vhsroni.exception.UserNotFoundException;
import ru.itis.vhsroni.model.CourseEntity;
import ru.itis.vhsroni.model.LessonEntity;
import ru.itis.vhsroni.model.UserEntity;
import ru.itis.vhsroni.repository.impl.CourseJpaRepositoryImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseJpaRepositoryImplTest {

    private static final Long ID = 1L;

    private static final String TITLE = "Test name";

    private static final String UPDATED_TITLE = "Updated title";

    private static final Set<UserEntity> USERS = new HashSet<>();

    private static final Set<LessonEntity> LESSONS = new HashSet<>();

    private static final Long USER_ID = 2L;

    private static final Long COURSES_COUNT = 10L;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CourseJpaRepositoryImpl courseRepository;

    private CourseEntity course;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        course = CourseEntity.builder()
                .id(ID)
                .title(TITLE)
                .users(USERS)
                .lessons(LESSONS)
                .build();
    }

    @Test
    void testSaveCorrect() {
        courseRepository.save(course);
        verify(entityManager, times(1)).persist(course);
    }

    @Test
    void testUpdateByIdCorrect() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(course);
        CourseEntity updated = new CourseEntity();
        updated.setTitle(UPDATED_TITLE);
        CourseEntity result = courseRepository.updateById(updated, ID);
        assertEquals(UPDATED_TITLE, result.getTitle());
    }

    @Test
    void testUpdateByIdWhenNotFound() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(null);
        assertThrows(CourseNotFoundException.class, () -> courseRepository.updateById(new CourseEntity(), ID));
    }

    @Test
    void testDeleteByIdCorrect() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(course);
        if (course.getUsers() == null) {
            course.setUsers(new HashSet<>());
        }
        courseRepository.deleteById(ID);
        verify(entityManager, times(1)).remove(course);
    }

    @Test
    void testDeleteByIdWhenNotFound() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(null);
        assertThrows(CourseNotFoundException.class, () -> courseRepository.deleteById(ID));
    }

    @Test
    void testFindAllCorrect() {
        TypedQuery<CourseEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(CourseEntity.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(course));
        List<CourseEntity> result = courseRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByIdIfEntityExists() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(course);
        Optional<CourseEntity> result = courseRepository.findById(ID);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdIfEntityNotFound() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(null);
        Optional<CourseEntity> result = courseRepository.findById(ID);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByTitleIfEntityExists() {
        TypedQuery<CourseEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(CourseEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(course);
        Optional<CourseEntity> result = courseRepository.findByTitle(TITLE);
        assertTrue(result.isPresent());
    }

    @Test
    void findByTitleIfEntityNotFound() {
        TypedQuery<CourseEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(CourseEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);
        Optional<CourseEntity> result = courseRepository.findByTitle(TITLE);
        assertFalse(result.isPresent());
    }


    @Test
    void testAddUserToCourseIfCourseNotFound() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(null);
        assertThrows(CourseNotFoundException.class, () -> courseRepository.addUserToCourse(ID, USER_ID));
    }

    @Test
    void testAddUserToCourseIfUserNotFound() {
        when(entityManager.find(CourseEntity.class, ID)).thenReturn(course);
        when(entityManager.find(UserEntity.class, USER_ID)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> courseRepository.addUserToCourse(ID, USER_ID));
    }

    @Test
    void testCountCoursesCorrect() {
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(COURSES_COUNT);
        Long count = courseRepository.countCourses();
        assertEquals(COURSES_COUNT, count);
    }
}
