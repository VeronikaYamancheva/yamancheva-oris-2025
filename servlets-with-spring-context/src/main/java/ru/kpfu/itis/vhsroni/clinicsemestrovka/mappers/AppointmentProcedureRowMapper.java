package ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentProcedureEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentProcedureRowMapper implements RowMapper<AppointmentProcedureEntity> {

    @Override
    public AppointmentProcedureEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AppointmentProcedureEntity.builder()
                .appointmentId(rs.getLong("appointment_id"))
                .procedureId(rs.getLong("procedure_id"))
                .build();
    }
}
