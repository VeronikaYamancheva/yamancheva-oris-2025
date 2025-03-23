package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String name);

    List<UserEntity> findUsersWithNoCourses();

    Long countUsersByCourseId(Long courseId);

    List<UserEntity> findUsersByCourseId(Long courseId);
}
