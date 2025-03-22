package ru.kpfu.itis.vhsroni.clinicsemestrovka.repository;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.util.List;
import java.util.Optional;

public interface ProcedureRepository {

    List<ProcedureEntity> getAllInsideSelectedRange(Integer pageSize, Integer offset);

    Integer getCount();

    List<ProcedureEntity> getAll();

    Optional<ProcedureEntity> save(String name, String description) throws DbException;

    Optional<ProcedureEntity> findById(Long id);

    void deleteById(Long id) throws DbException;

    Optional<ProcedureEntity> update(Long procedureId, String name, String description) throws DbException;
}
