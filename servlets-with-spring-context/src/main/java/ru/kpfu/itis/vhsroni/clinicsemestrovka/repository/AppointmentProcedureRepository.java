package ru.kpfu.itis.vhsroni.clinicsemestrovka.repository;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.util.List;
import java.util.Optional;

public interface AppointmentProcedureRepository {

    Optional<AppointmentProcedureEntity> save(Long appointmentId, Long procedureId) throws DbException;

    Optional<AppointmentProcedureEntity> findById(Long id);

    List<ProcedureEntity> findProceduresListByAppointmentId(Long id);
}
