package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.ProcedureDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ProcedureService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureDao procedureDao;

    @Override
    public List<ProcedureEntity> getServicesList(int page) {
        int pageSize = 7;
        int offset = (page - 1) * pageSize;
        List<ProcedureEntity> procedures = procedureDao.getAllInsideSelectedRange(pageSize, offset);
        return procedures;
    }

    @Override
    public Integer getCount() {
        return procedureDao.getCount();
    }

    @Override
    public Optional<ProcedureEntity> save(String name, String description) {
        return procedureDao.save(name, description);
    }

    @Override
    public void deleteById(Long id) throws DbException {
        procedureDao.deleteById(id);
    }

    @Override
    public Optional<ProcedureEntity> update(Long procedureId, String name, String description) throws DbException {
        return procedureDao.update(procedureId, name, description);
    }
}
