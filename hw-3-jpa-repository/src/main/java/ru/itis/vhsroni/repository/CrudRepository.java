package ru.itis.vhsroni.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {

    E save(E entity);

    E updateById(E entity, ID id);

    void deleteById(ID id);

    List<E> findAll();

    Optional<E> findById(ID id);

}
