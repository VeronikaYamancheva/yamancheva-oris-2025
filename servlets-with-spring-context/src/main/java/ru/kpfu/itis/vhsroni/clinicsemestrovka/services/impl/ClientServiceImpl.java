package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.ClientDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ClientService;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.HashUtil;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.PropertyReader;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.SignValidationUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    @Override
    public ClientSignResponseDto save(String email, String nickname, String password, String cpassword, String adminCode) throws IOException, ServletException, DbException {
        Map<String, String> errors = new HashMap<>();
        Map<String, String> formValidInputs = new HashMap<>();

        if (!SignValidationUtil.checkEmail(email))
            errors.put("email", "Email is not valid.");
        else if (!checkUniqueEmail(email))
            errors.put("email", "Email exists");
        else formValidInputs.put("email", email);

        if (!checkUniqueNickname(nickname))
            errors.put("nickname", "Nickname exists");
        else formValidInputs.put("nickname", nickname);

        if (!SignValidationUtil.checkPassword(password))
            errors.put("password", "Password is not valid");

        if (!SignValidationUtil.checkPassword(cpassword))
            errors.put("cpassword", "Confirm password is not valid");
        else if (!password.equals(cpassword)) {
            errors.put("cpassword", "Passwords don't match");
        }

        boolean isAdmin = checkIsAdmin(adminCode);

        if (errors.isEmpty()) {
            String hashPassword = HashUtil.hashPassword(password);
            Optional<ClientEntity> newClient = clientDao.save(email, nickname, hashPassword, isAdmin);
            return new ClientSignResponseDto(newClient, "", formValidInputs);
        } else {
            String err = convertErrorsMessagesToOneString(errors);
            return new ClientSignResponseDto(Optional.empty(), err, formValidInputs);
        }
    }

    @Override
    public ClientSignResponseDto authorize(String nickname, String password){

        Optional<ClientEntity> existingClient = clientDao.findByNickname(nickname);

        Map<String, String> errors = new HashMap<>();
        Map<String, String> formValidInputs = new HashMap<>();

        if (existingClient.isEmpty())
            errors.put("nickname", "There is no such client with this nickname.");
        else if (!HashUtil.verifyPassword(password, existingClient.get().getPassword()))
            errors.put("password", "Password don't match.");

        formValidInputs.put("nickname", nickname);
        if (errors.isEmpty()) {
            return new ClientSignResponseDto(existingClient, "", formValidInputs);
        } else {
            String err = convertErrorsMessagesToOneString(errors);
            return new ClientSignResponseDto(Optional.empty(), err, formValidInputs);
        }
    }

    @Override
    public ClientSignResponseDto update(String firstName, String lastName, String email) throws DbException {

        Map<String, String> errors = new HashMap<>();
        Map<String, String> formValidInputs = new HashMap<>();

        if (!SignValidationUtil.checkName(firstName)) errors.put("firstName", "FirstName is not valid.");
        else formValidInputs.put("firstName", firstName);

        if (!SignValidationUtil  .checkName(lastName)) errors.put("lastName", "LastName is not valid.");
        else formValidInputs.put("lastName", lastName);

        if (errors.isEmpty()) {
            Optional<ClientEntity> updatedClient = clientDao.update(firstName, lastName, email);
            return new ClientSignResponseDto(updatedClient, "", formValidInputs);
        } else {
            String err = convertErrorsMessagesToOneString(errors);
            return new ClientSignResponseDto(Optional.empty(), err, formValidInputs);
        }

    }

    @Override
    public boolean checkUniqueEmail(String email) {
        Optional<ClientEntity> client = clientDao.findByEmail(email);
        return client.isEmpty();
    }

    @Override
    public boolean checkUniqueNickname(String nickname) {
        Optional<ClientEntity> client = clientDao.findByNickname(nickname);
        return client.isEmpty();
    }

    @Override
    public boolean checkIsAdmin(String adminCode) {
        return HashUtil.verifyPassword(adminCode, PropertyReader.getProperty("ADMIN_CODE"));
    }

    @Override
    public List<ClientEntity> getAllNotAdmins() {
        return clientDao.getAllNotAdmins();
    }

    private String convertErrorsMessagesToOneString(Map<String, String> errors ) {
        String err = "";
        for (String key : errors.keySet()) {
            err += errors.get(key) + "; ";
        }
        return err;
    }

    @Override
    public String addPhotoId(Long clientId, String photoId) {
        Optional<ClientEntity> client = clientDao.addPhotoId(clientId, photoId);
        if (client.isEmpty()) {
            return null;
        } else {
            return client.get().getPhotoId();
        }

    }
}
