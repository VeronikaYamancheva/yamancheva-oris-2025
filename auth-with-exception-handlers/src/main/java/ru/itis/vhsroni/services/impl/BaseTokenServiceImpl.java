package ru.itis.vhsroni.services.impl;

import org.springframework.stereotype.Service;
import ru.itis.vhsroni.services.TokenService;

import java.util.UUID;

@Service
public class BaseTokenServiceImpl implements TokenService {

    @Override
    public String generateToken() {
        return "new-generated-token-%s".formatted(UUID.randomUUID());
    }
}
