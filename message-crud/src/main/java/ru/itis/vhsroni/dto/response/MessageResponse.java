package ru.itis.vhsroni.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record MessageResponse(UUID id, String author, String content, Instant sentAt, Instant lastUpdate) {
}
