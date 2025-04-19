package ru.kpfu.itis.vhsroni.clinicsemestrovka.services;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.util.List;
import java.util.Optional;

public interface DentistService {

    List<DentistEntity> getServicesList(int page);

    Integer getCount();

    Optional<DentistEntity> save(String firstName, String lastName, String email) throws DbException;

    void deleteById(Long id) throws DbException;

    Optional<DentistEntity> update(Long id, String firstName, String lastName, String email) throws DbException;
}
