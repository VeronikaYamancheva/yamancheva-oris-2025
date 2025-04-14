package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.annotation.MyTransactional;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {

    @MyTransactional
    E save(E entity);

    @MyTransactional
    E updateById(E entity, ID id);

    @MyTransactional
    void deleteById(ID id);

    @MyTransactional
    List<E> findAll();

    @MyTransactional
    Optional<E> findById(ID id);

}
