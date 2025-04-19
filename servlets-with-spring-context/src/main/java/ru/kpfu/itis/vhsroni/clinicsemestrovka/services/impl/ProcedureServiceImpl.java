package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.ProcedureRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ProcedureService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureRepository procedureRepository;

    @Override
    public List<ProcedureEntity> getServicesList(int page) {
        int pageSize = 7;
        int offset = (page - 1) * pageSize;
        List<ProcedureEntity> procedures = procedureRepository.getAllInsideSelectedRange(pageSize, offset);
        return procedures;
    }

    @Override
    public Integer getCount() {
        return procedureRepository.getCount();
    }

    @Override
    public Optional<ProcedureEntity> save(String name, String description) {
        return procedureRepository.save(name, description);
    }

    @Override
    public void deleteById(Long id) throws DbException {
        procedureRepository.deleteById(id);
    }

    @Override
    public Optional<ProcedureEntity> update(Long procedureId, String name, String description) throws DbException {
        return procedureRepository.update(procedureId, name, description);
    }
}
