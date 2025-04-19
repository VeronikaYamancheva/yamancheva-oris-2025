package ru.kpfu.itis.vhsroni.clinicsemestrovka.repository;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.util.List;
import java.util.Optional;

public interface DentistRepository {

    List<DentistEntity> getAllInsideSelectedRange(Integer pageSize, Integer offset);

    Integer getCount();

    List<DentistEntity> getAll();

    Optional<DentistEntity> findById(Long id);

    Optional<DentistEntity> save(String firstName, String lastName, String email) throws DbException;

    void deleteById(Long id) throws DbException;

    Optional<DentistEntity> update(Long id, String firstName, String lastName, String email) throws DbException;

}
