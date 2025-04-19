package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.ClientRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ClientService;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.validators.EmailValidator;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.validators.PasswordValidator;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.validators.UsernameValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientSignResponseDto save(String email, String nickname, String password, String cpassword, String adminCode) throws IOException, ServletException, DbException {
        Map<String, String> errors = new HashMap<>();
        Map<String, String> formValidInputs = new HashMap<>();

        if (!EmailValidator.checkEmail(email))
            errors.put("email", "Email is not valid.");
        else if (!checkUniqueEmail(email))
            errors.put("email", "Email exists");
        else formValidInputs.put("email", email);

        if (!checkUniqueNickname(nickname))
            errors.put("nickname", "Nickname exists");
        else formValidInputs.put("nickname", nickname);

        if (!PasswordValidator.checkPassword(password))
            errors.put("password", "Password is not valid");

        if (!PasswordValidator.checkPassword(cpassword))
            errors.put("cpassword", "Confirm password is not valid");
        else if (!password.equals(cpassword)) {
            errors.put("cpassword", "Passwords don't match");
        }

        boolean isAdmin = checkIsAdmin(adminCode);

        if (errors.isEmpty()) {
            String hashPassword = HashUtil.hashPassword(password);
            Optional<ClientEntity> newClient = clientRepository.save(email, nickname, hashPassword, isAdmin);
            return new ClientSignResponseDto(newClient, "", formValidInputs);
        } else {
            String err = convertErrorsMessagesToOneString(errors);
            return new ClientSignResponseDto(Optional.empty(), err, formValidInputs);
        }
    }

    @Override
    public ClientSignResponseDto update(String firstName, String lastName, String email) throws DbException {

        Map<String, String> errors = new HashMap<>();
        Map<String, String> formValidInputs = new HashMap<>();

        if (!UsernameValidator.checkName(firstName)) errors.put("firstName", "FirstName is not valid.");
        else formValidInputs.put("firstName", firstName);

        if (!UsernameValidator.checkName(lastName)) errors.put("lastName", "LastName is not valid.");
        else formValidInputs.put("lastName", lastName);

        if (errors.isEmpty()) {
            Optional<ClientEntity> updatedClient = clientRepository.update(firstName, lastName, email);
            return new ClientSignResponseDto(updatedClient, "", formValidInputs);
        } else {
            String err = convertErrorsMessagesToOneString(errors);
            return new ClientSignResponseDto(Optional.empty(), err, formValidInputs);
        }

    }

    @Override
    public boolean checkUniqueEmail(String email) {
        Optional<ClientEntity> client = clientRepository.findByEmail(email);
        return client.isEmpty();
    }

    @Override
    public boolean checkUniqueNickname(String nickname) {
        Optional<ClientEntity> client = clientRepository.findByNickname(nickname);
        return client.isEmpty();
    }

    @Override
    public boolean checkIsAdmin(String adminCode) {
        return HashUtil.verifyPassword(adminCode, BCrypt.hashpw(PropertyReader.getProperty("ADMIN_CODE"), BCrypt.gensalt()));
    }

    @Override
    public List<ClientEntity> getAllNotAdmins() {
        return clientRepository.getAllNotAdmins();
    }

    private String convertErrorsMessagesToOneString(Map<String, String> errors ) {
        String err = "";
        for (String key : errors.keySet()) {
            err += errors.get(key) + "; ";
        }
        return err;
    }
}
