package ru.kpfu.itis.vhsroni.clinicsemestrovka.dao;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface AppointmentDao {

    Optional<AppointmentEntity> save(Long clientId, Long dentistId, Date date, Time time) throws DbException;

    Optional<AppointmentEntity> findById(Long id);

    public List<AppointmentEntity> getAllByClientId(Long id);
}
