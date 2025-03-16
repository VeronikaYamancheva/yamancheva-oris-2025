package ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.ProcedureDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ProcedureRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ProcedureDaoImpl implements ProcedureDao {

    private final JdbcTemplate jdbcTemplate;

    private final ProcedureRowMapper mapper;

    //language=sql
    private static final String SQL_GET_ALL_INSIDE_SELECTED_RANGE = """
            SELECT * FROM procedure ORDER BY id LIMIT ? OFFSET ?;
            """;

    //language=sql
    private static final String SQL_GET_COUNT = """
            SELECT COUNT(*) FROM procedure;
            """;

    //language=sql
    private static final String SQL_GET_ALL = """
            SELECT * FROM procedure;
            """;

    //language=sql
    private static final String SQL_SAVE_NEW_PROCEDURE = """
            INSERT INTO procedure(name, description)
            VALUES (?, ?);
            """;

    //language=sql
    private static final String SQL_FIND_BY_ID = """
            SELECT * FROM procedure WHERE id = ?;;
            """;

    //language=sql
    private static final String SQL_UPDATE_BY_ID = """
            UPDATE procedure SET name = ?, description = ?
            WHERE id = ?;
            """;

    //language=sql
    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM procedure
            WHERE id = ?;
            """;

    @Override
    public List<ProcedureEntity> getAllInsideSelectedRange(Integer pageSize, Integer offset) {
        return jdbcTemplate.query(SQL_GET_ALL_INSIDE_SELECTED_RANGE, new Object[]{pageSize, offset}, mapper);
    }

    @Override
    public Integer getCount() {
        return jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
    }

    @Override
    public List<ProcedureEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, mapper);
    }

    @Override
    public Optional<ProcedureEntity> save(String name, String description) throws DbException {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SAVE_NEW_PROCEDURE, new String[] {"id"});
                int index = 0;
                ps.setString(++index, name);
                ps.setString(++index,description);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't save procedure into db.");
            throw new DbException("Problems with proceduret save.");
        }
    }

    @Override
    public Optional<ProcedureEntity> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("empty procedure in find by id method wuth id={}", id);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) throws DbException {
        try {
            int count = jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_DELETE_BY_ID, new String[] {"id"});
                ps.setLong(1, id);
                return ps;
            });
            if (count == 1) log.info("Delete-status - successful.");
            if (count == 0) log.info("There is no procedure with id = {} in db", id);
        } catch (Exception e) {
            log.warn("Can't delete procedure into db");
            throw new DbException("Problems with procedure delete.");
        }
    }

    @Override
    public Optional<ProcedureEntity> update(Long procedureId, String name, String description) throws DbException {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_UPDATE_BY_ID, new String[] {"id"});
                int index = 0;
                ps.setString(++index, name);
                ps.setString(++index, description);
                ps.setLong(++index, procedureId);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't update procedure");
            throw new DbException("Problems with procedure update.");
        }
    }
}
