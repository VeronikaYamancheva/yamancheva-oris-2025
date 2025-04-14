package ru.itis.vhsroni.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.api.MessageApi;
import ru.itis.vhsroni.dto.request.MessageSendingRequest;
import ru.itis.vhsroni.dto.request.MessageUpdatingRequest;
import ru.itis.vhsroni.dto.response.MessageResponse;
import ru.itis.vhsroni.services.MessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

    private final MessageService messageService;

    @Override
    public List<MessageResponse> getAll() {
        return messageService.findAll();
    }

    @Override
    public MessageResponse getById(UUID id) {
        return messageService.findById(id);
    }

    @Override
    public MessageResponse send(MessageSendingRequest request) {
        return messageService.send(request);
    }

    @Override
    public void deleteById(UUID id) {
        messageService.deleteById(id);
    }

    @Override
    public void update(UUID id, MessageUpdatingRequest request) {
        messageService.updateById(id, request);
    }
}
