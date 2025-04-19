package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.AppointmentService;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DentistRepository dentistRepository;

    private final ProcedureRepository procedureRepository;

    private final ClientRepository clientRepository;

    private final AppointmentProcedureRepository appointmentProcedureRepository;

    @Override
    public List<DentistEntity> getAllDentists() {
        return dentistRepository.getAll();
    }

    @Override
    public List<ProcedureEntity> getAllProcedures() {
        return procedureRepository.getAll();
    }

    @Override
    public Optional<AppointmentEntity> bookAppointment(String firstName, String lastName, String email, Long dentistId, Date date, Time time, Long[] proceduresId) throws DbException {

        Optional<ClientEntity> clientOptional = clientRepository.findByEmail(email);
        if (clientOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no client with such email.");
        }
        Long clientId = clientOptional.get().getId();

        Optional<DentistEntity> dentistOptional = dentistRepository.findById(dentistId);
        if (dentistOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no dentist with such ID.");
        }

        Optional<AppointmentEntity> savedAppointment = appointmentRepository.save(clientId, dentistId, date, time);

        for (int i = 0; i < proceduresId.length; i++) {
            appointmentProcedureRepository.save(savedAppointment.get().getId(), proceduresId[i]);
        }
        return savedAppointment;
    }

    @Override
    public List<Map<AppointmentEntity, List<ProcedureEntity>>> getAllWithProceduresByClientId(Long id) {
        List<AppointmentEntity> appointments = appointmentRepository.getAllByClientId(id);

        List<Map<AppointmentEntity, List<ProcedureEntity>>> result = new ArrayList<>();

        for (AppointmentEntity appointment : appointments) {
            List<ProcedureEntity> procedures = appointmentProcedureRepository.findProceduresListByAppointmentId(appointment.getId());
            Map<AppointmentEntity, List<ProcedureEntity>> appointmentProceduresMap = new HashMap<>();
            appointmentProceduresMap.put(appointment, procedures);
            result.add(appointmentProceduresMap);
        }
        return result;
    }
}
