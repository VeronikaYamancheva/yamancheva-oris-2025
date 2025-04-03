package ru.itis.vhsroni.exceptions;

import java.util.UUID;

public class MessageNotFoundException extends NotFoundServiceException{

    public MessageNotFoundException(UUID uuid) {
        super("Message with id = %s - not found".formatted(uuid));
    }

}
