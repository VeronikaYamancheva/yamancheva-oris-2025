package ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DentistRowMapper implements RowMapper<DentistEntity> {

    @Override
    public DentistEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DentistEntity.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .build();
    }
}
