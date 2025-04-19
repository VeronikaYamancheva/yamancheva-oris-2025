package ru.kpfu.itis.vhsroni.clinicsemestrovka.services;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DateTimeValidationException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AppointmentService {

    List<DentistEntity> getAllDentists();

    List<ProcedureEntity> getAllProcedures();

    Optional<AppointmentEntity> bookAppointment(String firstName, String lastName, String email, Long dentistId, Date date, Time time, Long[] proceduresId) throws DbException;

    List<Map<AppointmentEntity, List<ProcedureEntity>>> getAllWithProceduresByClientId(Long id);
}
