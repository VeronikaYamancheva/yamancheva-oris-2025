package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DateTimeValidationException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.AppointmentService;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDao appointmentDao;

    private final DentistDao dentistDao;

    private final ProcedureDao procedureDao;

    private final ClientDao clientDao;

    private final AppointmentProcedureDao appointmentProcedureDao;

    @Override
    public List<DentistEntity> getAllDentists() {
        return dentistDao.getAll();
    }

    @Override
    public List<ProcedureEntity> getAllProcedures() {
        return procedureDao.getAll();
    }

    @Override
    public Optional<AppointmentEntity> bookAppointment(String firstName, String lastName, String email, Long dentistId, Date date, Time time, Long[] proceduresId) throws DbException {

        Optional<ClientEntity> clientOptional = clientDao.findByEmail(email);
        if (clientOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no client with such email.");
        }
        Long clientId = clientOptional.get().getId();

        Optional<DentistEntity> dentistOptional = dentistDao.findById(dentistId);
        if (dentistOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no dentist with such ID.");
        }

        Optional<AppointmentEntity> savedAppointment = appointmentDao.save(clientId, dentistId, date, time);

        for (int i = 0; i < proceduresId.length; i++) {
            appointmentProcedureDao.save(savedAppointment.get().getId(), proceduresId[i]);
        }
        return savedAppointment;
    }

    @Override
    public List<Map<AppointmentEntity, List<ProcedureEntity>>> getAllWithProceduresByClientId(Long id) {
        List<AppointmentEntity> appointments = appointmentDao.getAllByClientId(id);

        List<Map<AppointmentEntity, List<ProcedureEntity>>> result = new ArrayList<>();

        for (AppointmentEntity appointment : appointments) {
            List<ProcedureEntity> procedures = appointmentProcedureDao.findProceduresListByAppointmentId(appointment.getId());
            Map<AppointmentEntity, List<ProcedureEntity>> appointmentProceduresMap = new HashMap<>();
            appointmentProceduresMap.put(appointment, procedures);
            result.add(appointmentProceduresMap);
        }
        return result;
    }
}
