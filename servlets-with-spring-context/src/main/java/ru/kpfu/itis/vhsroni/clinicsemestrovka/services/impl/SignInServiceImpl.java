package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.ClientRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.SignInService;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.HashUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final ClientRepository clientRepository;

    @Override
    public ClientSignResponseDto authorize(String nickname, String password) {
        Optional<ClientEntity> existingClient = clientRepository.findByNickname(nickname);

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

    private String convertErrorsMessagesToOneString(Map<String, String> errors ) {
        String err = "";
        for (String key : errors.keySet()) {
            err += errors.get(key) + "; ";
        }
        return err;
    }
}
