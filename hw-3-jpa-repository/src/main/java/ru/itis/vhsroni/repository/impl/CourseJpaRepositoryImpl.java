package ru.itis.vhsroni.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.exception.CourseNotFoundException;
import ru.itis.vhsroni.model.CourseEntity;
import ru.itis.vhsroni.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CourseJpaRepositoryImpl implements CourseRepository {

    private final EntityManager entityManager;

    private static final String JPQL_SELECT_ALL = "select course from CourseEntity course";


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
        entityManager.remove(id);
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
}
