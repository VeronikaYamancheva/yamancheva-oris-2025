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

    private static final String SELECT_BY_NAME = "select user from UserEntity user where user.name = :name";

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
            throw new IllegalArgumentException("User id " + id + " not found!");
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
        TypedQuery<UserEntity> query = entityManager.createQuery(SELECT_BY_NAME, UserEntity.class);
        query.setParameter("name", name);
        try {
            UserEntity user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Non unique users with name " + name);
        }
    }
}
