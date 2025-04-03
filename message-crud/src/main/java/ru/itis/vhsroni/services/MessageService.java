package ru.itis.vhsroni.services;

import ru.itis.vhsroni.dto.request.MessageSendingRequest;
import ru.itis.vhsroni.dto.request.MessageUpdatingRequest;
import ru.itis.vhsroni.dto.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<MessageResponse> findAll();

    MessageResponse findById(UUID uuid);

    MessageResponse send(MessageSendingRequest messageSendingRequest);

    void deleteById(UUID uuid);

    void updateById(UUID uuid, MessageUpdatingRequest messageRequestDto);
}
