package ru.itis.vhsroni.repository.impl;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.model.UserEntity;
import ru.itis.vhsroni.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    private static final String SELECT_BY_NAME = "select user from UserEntity user where user.name = :name";
    private static final String SELECT_ALL = "select user from UserEntity user";

    @Override
    public UserEntity save(UserEntity user) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(user);

        transaction.commit();
        return user;
    }

    @Override
    public UserEntity updateById(UserEntity entity, Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        UserEntity user = entityManager.find(UserEntity.class, id);
        if(user == null) {
            throw new IllegalArgumentException("User id " + id + " not found!");
        }

        user.setName(entity.getName());
        user.setCourses(entity.getCourses());

        transaction.commit();
        return user;
    }

    @Override
    public void deleteById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        UserEntity user = entityManager.find(UserEntity.class, id);
        if(user == null) {
            throw new IllegalArgumentException("User id " + id + " not found!");
        }
        entityManager.remove(user);

        transaction.commit();
    }

    @Override
    public List<UserEntity> findAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<UserEntity> query = entityManager.createQuery(SELECT_ALL, UserEntity.class);
        List<UserEntity> result = query.getResultList();

        transaction.commit();
        return result;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<UserEntity> query = entityManager.createQuery(SELECT_BY_NAME, UserEntity.class);
        query.setParameter("name", name);

        try {
            UserEntity user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("Non unique users with name " + name);
        } finally {
            transaction.commit();
        }
    }
}
