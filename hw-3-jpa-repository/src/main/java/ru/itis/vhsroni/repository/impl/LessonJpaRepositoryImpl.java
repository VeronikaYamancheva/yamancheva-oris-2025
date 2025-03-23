package ru.itis.vhsroni.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.exception.CourseNotFoundException;
import ru.itis.vhsroni.exception.LessonNotFoundException;
import ru.itis.vhsroni.model.CourseEntity;
import ru.itis.vhsroni.model.LessonEntity;
import ru.itis.vhsroni.repository.LessonRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LessonJpaRepositoryImpl implements LessonRepository {

    private final EntityManager entityManager;

    private static final String JPQL_SELECT_ALL = "select lesson from LessonEntity lesson";

    private static final String JPQL_COUNT_BY_COURSE_ID = """
            select count(lesson) from LessonEntity lesson where lesson.course.id = :courseId
            """;

    private static final String JPQL_FIND_BY_COURSE_ID = """
            select lesson from LessonEntity lesson where lesson.course.id = :courseId
            """;

    @Override
    @MyTransactional
    public LessonEntity save(LessonEntity lesson) {
        entityManager.persist(lesson);
        return lesson;
    }

    @Override
    @MyTransactional
    public LessonEntity updateById(LessonEntity entity, Long id) {
        LessonEntity lesson = entityManager.find(LessonEntity.class, id);
        if (lesson == null) {
            throw new LessonNotFoundException(id);
        }
        lesson.setName(entity.getName());
        lesson.setCourse(entity.getCourse());
        return lesson;
    }

    @Override
    @MyTransactional
    public void deleteById(Long id) {
        LessonEntity lesson = entityManager.find(LessonEntity.class, id);
        if (lesson == null) {
            throw new LessonNotFoundException(id);
        }
        entityManager.remove(lesson);
    }

    @Override
    @MyTransactional
    public List<LessonEntity> findAll() {
        TypedQuery<LessonEntity> query = entityManager.createQuery(JPQL_SELECT_ALL, LessonEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<LessonEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(LessonEntity.class, id));
    }

    @Override
    @MyTransactional
    public Long countLessonsByCourseId(Long courseId) {
        TypedQuery<Long> query = entityManager.createQuery(JPQL_COUNT_BY_COURSE_ID, Long.class);
        query.setParameter("courseId", courseId);
        return query.getSingleResult();
    }

    @Override
    @MyTransactional
    public void removeLessonFromCourse(Long courseId, Long lessonId) {
        CourseEntity course = entityManager.find(CourseEntity.class, courseId);
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        LessonEntity lesson = entityManager.find(LessonEntity.class, lessonId);
        if (lesson == null) {
            throw new LessonNotFoundException(lessonId);
        }
        if (lesson.getCourse().equals(course)) {
            entityManager.remove(lesson);
        }
    }

    @Override
    @MyTransactional
    public List<LessonEntity> findLessonsByCourseId(Long courseId) {
        TypedQuery<LessonEntity> query = entityManager.createQuery(JPQL_FIND_BY_COURSE_ID, LessonEntity.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }
}
