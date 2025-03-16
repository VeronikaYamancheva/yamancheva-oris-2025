package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.DentistDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.DentistService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    private final DentistDao dentistDao;

    @Override
    public List<DentistEntity> getServicesList(int page) {
        int pageSize = 7;
        int offset = (page - 1) * pageSize;
        List<DentistEntity> dentists = dentistDao.getAllInsideSelectedRange(pageSize, offset);
        return dentists;
    }

    @Override
    public Integer getCount() {
        return dentistDao.getCount();
    }

    @Override
    public Optional<DentistEntity> save(String firstName, String lastName, String email) throws DbException {
        return dentistDao.save(firstName, lastName, email);
    }

    @Override
    public void deleteById(Long id) throws DbException {
        dentistDao.deleteById(id);
    }

    @Override
    public Optional<DentistEntity> update(Long id, String firstName, String lastName, String email) throws DbException {
        return dentistDao.update(id, firstName, lastName, email);
    }
}
