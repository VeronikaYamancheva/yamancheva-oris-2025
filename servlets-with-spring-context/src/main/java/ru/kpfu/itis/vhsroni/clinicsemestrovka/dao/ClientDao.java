package ru.kpfu.itis.vhsroni.clinicsemestrovka.dao;

import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    Optional<ClientEntity> save (String email, String nickname, String hashPassword, boolean isAdmin) throws DbException;

    Optional<ClientEntity> findById (Long id);

    Optional<ClientEntity> findByEmail(String email);

    Optional<ClientEntity> findByNickname(String nickname);

    Optional<ClientEntity> update(String firstName, String lastName, String email) throws DbException;

    List<ClientEntity> getAllNotAdmins();

    Optional<ClientEntity> addPhotoId(Long clientId, String photoId);

}
