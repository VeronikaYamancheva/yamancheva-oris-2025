package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @MyTransactional
    Optional<UserEntity> findByName(String name);

    @MyTransactional
    List<UserEntity> findUsersWithNoCourses();

    @MyTransactional
    Long countUsersByCourseId(Long courseId);

    @MyTransactional
    List<UserEntity> findUsersByCourseId(Long courseId);
}
