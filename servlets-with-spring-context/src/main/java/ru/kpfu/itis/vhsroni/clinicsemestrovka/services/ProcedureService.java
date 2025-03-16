package ru.kpfu.itis.vhsroni.clinicsemestrovka.services;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.util.List;
import java.util.Optional;

public interface ProcedureService {

    List<ProcedureEntity> getServicesList(int page);

    Integer getCount();

    Optional<ProcedureEntity> save(String name, String description);

    void deleteById(Long id) throws DbException;

    Optional<ProcedureEntity> update(Long procedureId, String name, String description) throws DbException;
}
