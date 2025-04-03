package ru.itis.vhsroni.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.itis.vhsroni.dto.request.MessageSendingRequest;
import ru.itis.vhsroni.dto.request.MessageUpdatingRequest;
import ru.itis.vhsroni.dto.response.MessageResponse;
import ru.itis.vhsroni.entities.MessageEntity;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "sentAt", expression = "java(java.time.Instant.now())"),
            @Mapping(target = "lastUpdate", expression = "java(java.time.Instant.now())")
    })
    MessageEntity toEntity(MessageSendingRequest request);

    MessageResponse toResponse(MessageEntity message);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "sentAt", ignore = true),
            @Mapping(target = "lastUpdate", expression = "java(java.time.Instant.now())")
    })
    void updateFromRequest(MessageUpdatingRequest request, @MappingTarget MessageEntity message);
}
