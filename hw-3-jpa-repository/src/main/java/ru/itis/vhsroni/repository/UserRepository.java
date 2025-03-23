package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String name);

}
