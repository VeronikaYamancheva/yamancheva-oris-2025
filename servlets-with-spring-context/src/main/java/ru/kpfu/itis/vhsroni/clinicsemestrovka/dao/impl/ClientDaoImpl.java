package ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.ClientDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ClientRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ClientDaoImpl implements ClientDao {

    private final JdbcTemplate jdbcTemplate;

    private final ClientRowMapper rowMapper;

    //language=sql
    private static final String SQL_SAVE_NEW_CLIENT = """
            INSERT INTO client(email, nickname, password, is_admin)
            VALUES (?, ?, ?, ?);
            """;

    //language=sql
    private static final String SQL_FIND_BY_ID = """
            SELECT * FROM client WHERE id = ?;
            """;

    //language=sql
    private static final String SQL_FIND_BY_EMAIL = """
            SELECT * FROM client WHERE email = ?;
            """;

    //language=sql
    private static final String SQL_FIND_BY_NICKNAME = """
            SELECT * FROM client WHERE nickname = ?;
            """;

    //language=sql
    private static final String SQL_UPDATE = """
            UPDATE client SET first_name = ?, last_name = ?
            WHERE email = ?;
            """;

    //language=sql
    private static final String SQL_GET_ALL_NOT_ADMINS = """
            SELECT * FROM client WHERE is_admin = false;
            """;

    //language=sql
    private static final String SQL_SET_PHOTO_ID = """
            UPDATE client SET photoid = ? WHERE id = ?;
            """;


    @Override
    public Optional<ClientEntity> save(String email, String nickname, String hashPassword, boolean isAdmin) throws DbException {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SAVE_NEW_CLIENT, new String[]{"id"});
                int index = 0;
                ps.setString(++index, email);
                ps.setString(++index, nickname);
                ps.setString(++index, hashPassword);
                ps.setBoolean(++index, isAdmin);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't save client.");
            throw new DbException("Problems with client save.", e);
        }
    }

    @Override
    public Optional<ClientEntity> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("EmptyResultDataAccessException in findById");
            return Optional.empty();
        }
    }

    @Override
    public Optional<ClientEntity> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("EmptyResultDataAccessException in findByEmail");
            return Optional.empty();
        }
    }

    @Override
    public Optional<ClientEntity> findByNickname(String nickname) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_NICKNAME, new Object[]{nickname}, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.warn("EmptyResultDataAccessException in findByNickname");
            return Optional.empty();
        }
    }

    @Override
    public Optional<ClientEntity> update(String firstName, String lastName, String email) throws DbException {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_UPDATE, new String[]{"id"});
                int index = 0;
                ps.setString(++index, firstName);
                ps.setString(++index, lastName);
                ps.setString(++index, email);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't update client");
            throw new DbException("Problems with client update", e);
        }
    }

    @Override
    public Optional<ClientEntity> addPhotoId(Long clientId, String photoId) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SET_PHOTO_ID, new String[]{"id"});
                int index = 0;
                ps.setString(++index, photoId);
                ps.setLong(++index, clientId);
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findById(id);
        } catch (Exception e) {
            log.warn("Can't set photo to client");
            throw new DbException("Problems with client's photo update", e);
        }
    }

    @Override
    public List<ClientEntity> getAllNotAdmins() {
        return jdbcTemplate.query(SQL_GET_ALL_NOT_ADMINS, rowMapper);
    }
}
