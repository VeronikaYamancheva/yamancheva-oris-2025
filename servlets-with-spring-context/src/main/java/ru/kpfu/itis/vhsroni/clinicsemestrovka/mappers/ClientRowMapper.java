package ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClientRowMapper implements RowMapper<ClientEntity> {

    @Override
    public ClientEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClientEntity.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .nickname(rs.getString("nickname"))
                .password(rs.getString("password"))
                .isAdmin(rs.getBoolean("is_admin"))
                .build();
    }
}
