package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.DentistRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.DentistService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    private final DentistRepository dentistRepository;

    @Override
    public List<DentistEntity> getServicesList(int page) {
        int pageSize = 7;
        int offset = (page - 1) * pageSize;
        List<DentistEntity> dentists = dentistRepository.getAllInsideSelectedRange(pageSize, offset);
        return dentists;
    }

    @Override
    public Integer getCount() {
        return dentistRepository.getCount();
    }

    @Override
    public Optional<DentistEntity> save(String firstName, String lastName, String email) throws DbException {
        return dentistRepository.save(firstName, lastName, email);
    }

    @Override
    public void deleteById(Long id) throws DbException {
        dentistRepository.deleteById(id);
    }

    @Override
    public Optional<DentistEntity> update(Long id, String firstName, String lastName, String email) throws DbException {
        return dentistRepository.update(id, firstName, lastName, email);
    }
}
