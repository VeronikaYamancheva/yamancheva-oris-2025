package ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.AppointmentDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.AppointmentRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AppointmentDaoImpl implements AppointmentDao {

    private final JdbcTemplate jdbcTemplate;

    private final AppointmentRowMapper mapper;


    //language=sql
    private static final String SQL_SAVE_NEW_APPOINTMENT = """
            INSERT INTO appointment(client_id, dentist_id, appointment_date, appointment_time)
            VALUES (?, ?, ?, ?);
            """;

    //language=sql
    private static final String SQL_FIND_BY_ID = """
            SELECT * FROM appointment WHERE id = ?;
            """;

    //language=sql
    private static final String SQL_GET_ALL_BY_CLIENT_ID = """
            SELECT * FROM appointment WHERE client_id = ?;
            """;

    @Override
    public Optional<AppointmentEntity> save(Long clientId, Long dentistId, Date date, Time time) throws DbException{
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SAVE_NEW_APPOINTMENT, new String[]{"id"});
                int index = 0;
                ps.setLong(++index, clientId);
                ps.setLong(++index, dentistId);
                ps.setDate(++index, date);
                ps.setTime(++index, time);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't save appointment into db.");
            throw new DbException("Problems with appointment save.", e);
        }
    }

    @Override
    public Optional<AppointmentEntity> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Empty appointment in find by id method wuth id={}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<AppointmentEntity> getAllByClientId(Long id) {
        return jdbcTemplate.query(SQL_GET_ALL_BY_CLIENT_ID, new Object[]{id}, mapper);
    }
}
