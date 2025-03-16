package ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.ClientDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.InputUniqueValidatorService;

import java.util.Optional;

@RequiredArgsConstructor
public class InputUniqueValidationServiceImpl implements InputUniqueValidatorService {

    private final ClientDao clientDao;
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
}
