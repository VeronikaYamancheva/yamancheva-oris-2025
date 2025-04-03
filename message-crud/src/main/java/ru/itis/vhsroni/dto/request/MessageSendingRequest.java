package ru.itis.vhsroni.dto.request;

import lombok.Builder;

@Builder
public record MessageSendingRequest(String author, String content) {
}
