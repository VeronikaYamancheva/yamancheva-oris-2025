package ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;import org.springframework.jdbc.support.KeyHolder;import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.DentistDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.DentistRowMapper;

import java.sql.PreparedStatement;import java.util.List;
import java.util.Objects;import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DentistDaoImpl implements DentistDao {

    private final JdbcTemplate jdbcTemplate;

    private final DentistRowMapper mapper;

    //language=sql
    private static final String SQL_GET_ALL_INSIDE_SELECTED_RANGE = """
            SELECT * FROM dentist ORDER BY id LIMIT ? OFFSET ?;
            """;

    //language=sql
    private static final String SQL_GET_COUNT = """
            SELECT COUNT(*) FROM dentist;
            """;

    //language=sql
    private static final String SQL_GET_ALL = """
            SELECT * FROM dentist;
            """;

    //language=sql
    private static final String SQL_FIND_BY_ID = """
            SELECT * FROM dentist WHERE id = ?;;
            """;

    //language=sql
    private static final String SQL_SAVE = """
            INSERT INTO dentist(first_name, last_name, email)
            VALUES (?, ?, ?);
            """;

    //language=sql
    private static final String SQL_UPDATE_BY_ID = """
            UPDATE dentist SET first_name = ?, last_name = ?, email = ?
            WHERE id = ?;
            """;

    //language=sql
    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM dentist
            WHERE id = ?;
            """;

    @Override
    public List<DentistEntity> getAllInsideSelectedRange(Integer pageSize, Integer offset) {
        return jdbcTemplate.query(SQL_GET_ALL_INSIDE_SELECTED_RANGE, new Object[]{pageSize, offset}, mapper);
    }

    @Override
    public Integer getCount() {
        return jdbcTemplate.queryForObject(SQL_GET_COUNT, Integer.class);
    }

    @Override
    public List<DentistEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, mapper);
    }

    @Override
    public Optional<DentistEntity> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, mapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Empty dentist in find by id method wuth id={}", id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<DentistEntity> save(String firstName, String lastName, String email) throws DbException{
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SAVE, new String[] {"id"});
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't save dentist into db");
            throw new DbException("Problems with dentist save.", e);
        }
    }

    @Override
    public void deleteById (Long id) throws DbException{
        try {
            int count = jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_DELETE_BY_ID, new String[] {"id"});
                ps.setLong(1, id);
                return ps;
            });
            if (count == 1) log.info("Delete-status - successful.");
            if (count == 0) log.info("There is no dentist with id = {} in db", id);
        } catch (Exception e) {
            log.warn("Can't delete dentist into db");
            throw new DbException("Problems with dentist delete.", e);
        }
    }

    @Override
    public Optional<DentistEntity> update(Long dentistId, String firstName, String lastName, String email) throws DbException{
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_UPDATE_BY_ID, new String[] {"id"});
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setLong(4, dentistId);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't update dentist");
            throw new DbException("Problems with dentist update.", e);
        }
    }
}


