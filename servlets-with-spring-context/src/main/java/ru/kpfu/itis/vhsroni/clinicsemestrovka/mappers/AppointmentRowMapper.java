package ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentRowMapper implements RowMapper<AppointmentEntity> {

    @Override
    public AppointmentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AppointmentEntity.builder()
                .id(rs.getLong("id"))
                .clientId(rs.getLong("client_id"))
                .dentistId(rs.getLong("dentist_id"))
                .date(rs.getDate("appointment_date"))
                .time(rs.getTime("appointment_time"))
                .build();
    }
}
