package ru.itis.vhsroni.repository.impl;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.exception.UserNotFoundException;
import ru.itis.vhsroni.model.UserEntity;
import ru.itis.vhsroni.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    private static final String JPQL_SELECT_ALL = "select user from UserEntity user";

    private static final String JPQL_SELECT_BY_NAME = "select user from UserEntity user where user.name = :name";

    private static final String JPQL_FIND_WITH_NO_COURSES = """
            select user from UserEntity user where user.courses is empty
            """;

    private static final String JPQL_COUNT_BY_COURSE_ID = """
            select count(user) from UserEntity user join user.courses course where course.id = :courseId
            """;

    private static final String JPQL_FIND_BY_COURSE_ID = """
            select user from UserEntity user join user.courses course where course.id = :courseId
            """;

    @Override
    @MyTransactional
    public UserEntity save(UserEntity user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    @MyTransactional
    public UserEntity updateById(UserEntity entity, Long id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        if(user == null) {
            throw new UserNotFoundException(id);
        }
        user.setName(entity.getName());
        user.setCourses(entity.getCourses());
        return user;
    }

    @Override
    @MyTransactional
    public void deleteById(Long id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        if(user == null) {
            throw new UserNotFoundException(id);
        }
        entityManager.remove(user);
    }

    @Override
    @MyTransactional
    public List<UserEntity> findAll() {
        TypedQuery<UserEntity> query = entityManager.createQuery(JPQL_SELECT_ALL, UserEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    @Override
    @MyTransactional
    public Optional<UserEntity> findByName(String name) {
        TypedQuery<UserEntity> query = entityManager.createQuery(JPQL_SELECT_BY_NAME, UserEntity.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            throw new UserNotFoundException(name);
        }
    }

    @Override
    @MyTransactional
    public List<UserEntity> findUsersWithNoCourses() {
        TypedQuery<UserEntity> query = entityManager.createQuery(JPQL_FIND_WITH_NO_COURSES, UserEntity.class);
        return query.getResultList();
    }

    @Override
    @MyTransactional
    public Long countUsersByCourseId(Long courseId) {
        TypedQuery<Long> query = entityManager.createQuery(JPQL_COUNT_BY_COURSE_ID, Long.class);
        query.setParameter("courseId", courseId);
        return query.getSingleResult();
    }

    @Override
    @MyTransactional
    public List<UserEntity> findUsersByCourseId(Long courseId) {
        TypedQuery<UserEntity> query = entityManager.createQuery(JPQL_FIND_BY_COURSE_ID, UserEntity.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

}
