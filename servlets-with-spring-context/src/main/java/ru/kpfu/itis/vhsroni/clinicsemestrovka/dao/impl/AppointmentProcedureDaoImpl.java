package ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.AppointmentProcedureDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.AppointmentProcedureRowMapper;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ProcedureRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AppointmentProcedureDaoImpl implements AppointmentProcedureDao {

    private final JdbcTemplate jdbcTemplate;

    private final AppointmentProcedureRowMapper mapper;

    private final ProcedureRowMapper procedureRowMapper;

    //language=sql
    private static final String SQL_SAVE_NEW_APPOINTMENT_PROCEDURE = """
            INSERT INTO appointment_procedure(appointment_id, procedure_id)
            VALUES (?, ?);
            """;

    //language=sql
    private static final String SQL_FIND_BY_ID = """
            SELECT * FROM appointment_procedure WHERE id = ?;
            """;

    //language=sql
    private static final String SQL_FIND_PROCEDURE_BY_APPOINTMENT_ID = """
            SELECT p.* FROM procedure p 
            JOIN appointment_procedure ap ON p.id = ap.procedure_id 
            WHERE ap.appointment_id = ?;
            """;


    @Override
    public Optional<AppointmentProcedureEntity> save(Long appointmentId, Long procedureId) throws DbException {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SAVE_NEW_APPOINTMENT_PROCEDURE, new String[]{"id"});
                int index = 0;
                ps.setLong(++index,appointmentId);
                ps.setLong(++index, procedureId);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't save appointment_procedure");
            throw new DbException("Problems with appointment_procedure save.", e);
        }
    }

    @Override
    public Optional<AppointmentProcedureEntity> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Empty appointment in find by id method wuth id={}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<ProcedureEntity> findProceduresListByAppointmentId(Long id) {
        return jdbcTemplate.query(SQL_FIND_PROCEDURE_BY_APPOINTMENT_ID, new Object[]{id}, procedureRowMapper);
    }
}
