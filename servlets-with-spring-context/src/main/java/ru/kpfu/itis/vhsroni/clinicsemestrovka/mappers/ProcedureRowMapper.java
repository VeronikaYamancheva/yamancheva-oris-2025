package ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedureRowMapper implements RowMapper<ProcedureEntity> {

    @Override
    public ProcedureEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProcedureEntity.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
    }
}
