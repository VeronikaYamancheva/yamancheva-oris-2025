package ru.kpfu.itis.vhsroni.clinicsemestrovka.services;

import jakarta.servlet.ServletException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;

import java.io.IOException;
import java.util.List;

public interface ClientService {

    ClientSignResponseDto save(String email, String nickname, String password, String cpassword, String adminCode) throws IOException, ServletException, DbException;

    ClientSignResponseDto authorize(String email, String nickname) throws IOException, ServletException;

    ClientSignResponseDto update(String firstName, String lastName, String email) throws DbException;

    boolean checkUniqueEmail(String email);

    boolean checkUniqueNickname(String nickname);

    boolean checkIsAdmin(String adminCode);

    List<ClientEntity> getAllNotAdmins();

    String addPhotoId (Long clientId, String photoId);
}
