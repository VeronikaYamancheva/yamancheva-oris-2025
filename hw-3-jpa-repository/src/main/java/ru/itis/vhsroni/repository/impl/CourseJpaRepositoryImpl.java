package ru.itis.vhsroni.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.exception.CourseNotFoundException;
import ru.itis.vhsroni.exception.UserNotFoundException;
import ru.itis.vhsroni.model.CourseEntity;
import ru.itis.vhsroni.model.UserEntity;
import ru.itis.vhsroni.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CourseJpaRepositoryImpl implements CourseRepository {

    private final EntityManager entityManager;

    private static final String JPQL_SELECT_ALL = "select course from CourseEntity course";

    private static final String JPQL_SELECT_COURSES_COUNT = "select count(course) from CourseEntity course";

    private static final String JPQL_FIND_BY_TITLE = "select course from CourseEntity course where course.title = :title";

    @Override
    @MyTransactional
    public CourseEntity save(CourseEntity course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    @MyTransactional
    public CourseEntity updateById(CourseEntity entity, Long id) {
        CourseEntity course = entityManager.find(CourseEntity.class, id);
        if (course == null) {
            throw new CourseNotFoundException(id);
        }
        course.setTitle(entity.getTitle());
        course.setUsers(entity.getUsers());
        course.setLessons(entity.getLessons());
        return course;
    }

    @Override
    @MyTransactional
    public void deleteById(Long id) {
        CourseEntity course = entityManager.find(CourseEntity.class, id);
        if (course == null) {
            throw new CourseNotFoundException(id);
        }
        entityManager.remove(course);
    }

    @Override
    @MyTransactional
    public List<CourseEntity> findAll() {
        TypedQuery<CourseEntity> query = entityManager.createQuery(JPQL_SELECT_ALL, CourseEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<CourseEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CourseEntity.class, id));
    }

    @Override
    @MyTransactional
    public Long countCourses() {
        TypedQuery<Long> query = entityManager.createQuery(JPQL_SELECT_COURSES_COUNT, Long.class);
        return query.getSingleResult();
    }

    @Override
    @MyTransactional
    public Optional<CourseEntity> findByTitle(String title) {
        TypedQuery<CourseEntity> query = entityManager.createQuery(JPQL_FIND_BY_TITLE, CourseEntity.class);
        query.setParameter("title", title);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            throw new CourseNotFoundException(title);
        }
    }

    @Override
    @MyTransactional
    public void addUserToCourse(Long courseId, Long userId) {
        CourseEntity course = entityManager.find(CourseEntity.class, courseId);
        if (course == null) {
            throw new CourseNotFoundException(courseId);
        }
        UserEntity user = entityManager.find(UserEntity.class, userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        course.getUsers().add(user);
        user.getCourses().add(course);
    }
}

