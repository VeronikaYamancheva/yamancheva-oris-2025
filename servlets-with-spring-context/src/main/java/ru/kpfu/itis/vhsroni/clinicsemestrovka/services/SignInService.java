package ru.kpfu.itis.vhsroni.clinicsemestrovka.services;

import jakarta.servlet.ServletException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.io.IOException;

public interface SignInService {

    ClientSignResponseDto authorize(String email, String nickname) throws IOException, ServletException;
}
