package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itis.vhsroni.exception.CourseNotFoundException;
import ru.itis.vhsroni.exception.LessonNotFoundException;
import ru.itis.vhsroni.model.CourseEntity;
import ru.itis.vhsroni.model.LessonEntity;
import ru.itis.vhsroni.repository.impl.LessonJpaRepositoryImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonJpaRepositoryImplTest {

    private static final Long ID = 1L;

    private static final String NAME = "Lesson name";

    private static final String UPDATED_NAME = "Updated name";

    private static final CourseEntity COURSE = new CourseEntity();

    private static final Long LESSONS_COUNT = 5L;

    private static final Long COURSE_ID = 1L;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private LessonJpaRepositoryImpl lessonRepository;

    private LessonEntity lesson;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        lesson = LessonEntity.builder()
                .id(ID)
                .name(NAME)
                .course(COURSE)
                .build();
    }

    @Test
    void testSaveCorrect() {
        lessonRepository.save(lesson);
        verify(entityManager, times(1)).persist(lesson);
    }

    @Test
    void testUpdateByIdWithExistingEntity() {
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(lesson);
        LessonEntity updated = new LessonEntity();
        updated.setName(UPDATED_NAME);
        LessonEntity result = lessonRepository.updateById(updated, ID);
        assertEquals(UPDATED_NAME, result.getName());
    }

    @Test
    void testUpdateByIdIfLessonNotFound() {
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(null);
        assertThrows(LessonNotFoundException.class, () -> lessonRepository.updateById(new LessonEntity(), ID));
    }

    @Test
    void testDeleteByIdCorrect() {
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(lesson);
        lessonRepository.deleteById(ID);
        verify(entityManager, times(1)).remove(lesson);
    }

    @Test
    void testDeleteByIdIfLessonNotFound() {
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(null);
        assertThrows(LessonNotFoundException.class, () -> lessonRepository.deleteById(ID));
    }

    @Test
    void testFindAllCorrect() {
        TypedQuery<LessonEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(LessonEntity.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(lesson));
        List<LessonEntity> result = lessonRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByIdIfExists() {
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(lesson);
        Optional<LessonEntity> result = lessonRepository.findById(ID);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdIfNotFound() {
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(null);
        Optional<LessonEntity> result = lessonRepository.findById(ID);
        assertFalse(result.isPresent());
    }

    @Test
    void testCountLessonsByCourseIdCorrect() {
        TypedQuery<Long> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(LESSONS_COUNT);
        Long count = lessonRepository.countLessonsByCourseId(ID);
        assertEquals(LESSONS_COUNT, count);
    }

    @Test
    void testRemoveLessonFromCourseIfLessonExists() {
        CourseEntity course = new CourseEntity();
        course.setId(ID);
        lesson.setCourse(course);
        when(entityManager.find(CourseEntity.class, COURSE_ID)).thenReturn(course);
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(lesson);
        lessonRepository.removeLessonFromCourse(COURSE_ID, ID);
        verify(entityManager, times(1)).remove(lesson);
    }

    @Test
    void testRemoveLessonFromCourseIfCourseNotFound() {
        when(entityManager.find(CourseEntity.class, COURSE_ID)).thenReturn(null);
        assertThrows(CourseNotFoundException.class, () -> lessonRepository.removeLessonFromCourse(COURSE_ID, ID));
    }

    @Test
    void testRemoveLessonFromCourseIfLessonNotFound() {
        when(entityManager.find(CourseEntity.class, COURSE_ID)).thenReturn(new CourseEntity());
        when(entityManager.find(LessonEntity.class, ID)).thenReturn(null);
        assertThrows(LessonNotFoundException.class, () -> lessonRepository.removeLessonFromCourse(COURSE_ID, ID));
    }

    @Test
    void testFindLessonsByCourseIdCorrect() {
        TypedQuery<LessonEntity> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(LessonEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(lesson));
        List<LessonEntity> result = lessonRepository.findLessonsByCourseId(COURSE_ID);
        assertEquals(1, result.size());
    }
}
