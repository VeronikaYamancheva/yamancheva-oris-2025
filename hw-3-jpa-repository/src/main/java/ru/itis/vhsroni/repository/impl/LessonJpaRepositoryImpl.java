package ru.itis.vhsroni.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.exception.LessonNotFoundException;
import ru.itis.vhsroni.model.LessonEntity;
import ru.itis.vhsroni.repository.LessonRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LessonJpaRepositoryImpl implements LessonRepository {

    private final EntityManager entityManager;

    private static final String JPQL_SELECT_ALL = "select lesson from LessonEntity lesson";

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
}
