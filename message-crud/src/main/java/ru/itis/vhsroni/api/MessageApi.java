package ru.itis.vhsroni.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.dto.request.MessageSendingRequest;
import ru.itis.vhsroni.dto.request.MessageUpdatingRequest;
import ru.itis.vhsroni.dto.response.MessageResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/messages")
public interface MessageApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<MessageResponse> getAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    MessageResponse getById(@PathVariable("id") UUID id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MessageResponse send(@RequestBody MessageSendingRequest request);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") UUID id);

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void update(@PathVariable("id") UUID id, @RequestBody MessageUpdatingRequest request);
}
