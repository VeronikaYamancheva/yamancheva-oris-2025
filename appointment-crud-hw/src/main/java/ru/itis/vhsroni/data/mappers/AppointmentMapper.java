package ru.itis.vhsroni.data.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.itis.vhsroni.data.entities.AppointmentEntity;
import ru.itis.vhsroni.data.entities.ClientEntity;
import ru.itis.vhsroni.data.entities.DentistEntity;
import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.dto.response.AppointmentResponse;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mappings({
            @Mapping(target = "appointmentId", source = "appointmentId"),
            @Mapping(target = "date", source = "date"),
            @Mapping(target = "time", source = "time"),
            @Mapping(target = "clientFirstName", expression = "java(entity.getClient().getUser().getFirstName())"),
            @Mapping(target = "clientLastName", expression = "java(entity.getClient().getUser().getLastName())"),
            @Mapping(target = "clientPatronymic", expression = "java(entity.getClient().getUser().getPatronymic())"),
            @Mapping(target = "clientEmail", expression = "java(entity.getClient().getUser().getEmail())"),
            @Mapping(target = "clientPhone", expression = "java(entity.getClient().getUser().getPhoneNumber())"),
            @Mapping(target = "dentistFirstName", expression = "java(entity.getDentist().getUser().getFirstName())"),
            @Mapping(target = "dentistLastName", expression = "java(entity.getDentist().getUser().getLastName())"),
            @Mapping(target = "dentistPatronymic", expression = "java(entity.getDentist().getUser().getPatronymic())"),
            @Mapping(target = "dentistEmail", expression = "java(entity.getDentist().getUser().getEmail())"),
            @Mapping(target = "dentistPhone", expression = "java(entity.getDentist().getUser().getPhoneNumber())")
    })
    AppointmentDetailedResponse toDetailedResponse(AppointmentEntity entity);

    @Mappings({
            @Mapping(source = "client", target = "clientId", qualifiedByName = "extractClientId"),
            @Mapping(source = "dentist", target = "dentistId", qualifiedByName = "extractDentistId")
    })
    AppointmentResponse toResponse(AppointmentEntity entity);

    @Named("extractClientId")
    static UUID extractClientId(ClientEntity client) {
        return client != null ? client.getClientId() : null;
    }

    @Named("extractDentistId")
    static UUID extractDentistId(DentistEntity dentist) {
        return dentist != null ? dentist.getDentistId() : null;
    }
}
