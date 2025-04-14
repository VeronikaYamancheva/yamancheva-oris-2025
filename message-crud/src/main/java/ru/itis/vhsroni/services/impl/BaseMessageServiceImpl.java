package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vhsroni.dto.request.MessageSendingRequest;
import ru.itis.vhsroni.dto.request.MessageUpdatingRequest;
import ru.itis.vhsroni.dto.response.MessageResponse;
import ru.itis.vhsroni.entities.MessageEntity;
import ru.itis.vhsroni.exceptions.MessageNotFoundException;
import ru.itis.vhsroni.mappers.MessageMapper;
import ru.itis.vhsroni.repositories.MessageRepository;
import ru.itis.vhsroni.services.MessageService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseMessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponse> findAll() {
        return messageRepository.findAll()
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MessageResponse findById(UUID id) {
        return messageRepository.findById(id)
                .map(messageMapper::toResponse)
                .orElseThrow(() -> new MessageNotFoundException(id));
    }

    @Override
    public MessageResponse send(MessageSendingRequest messageSendingRequest) {
        MessageEntity message = messageMapper.toEntity(messageSendingRequest);
        MessageEntity savedMessage = messageRepository.save(message);
        return messageMapper.toResponse(savedMessage);
    }

    @Override
    public void deleteById(UUID id) {
        if (!messageRepository.existsById(id)) {
            throw new MessageNotFoundException(id);
        }
        messageRepository.deleteById(id);
    }

    @Override
    public void updateById(UUID id, MessageUpdatingRequest messageRequestDto) {
        MessageEntity message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));
        messageMapper.updateFromRequest(messageRequestDto, message);
        messageRepository.save(message);
    }
}
