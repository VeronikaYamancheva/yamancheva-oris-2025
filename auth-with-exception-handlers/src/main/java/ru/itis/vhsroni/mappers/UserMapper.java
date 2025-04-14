package ru.itis.vhsroni.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mindrot.jbcrypt.BCrypt;
import ru.itis.vhsroni.api.dto.request.SignUpRequest;
import ru.itis.vhsroni.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "confirmationCode", ignore = true)
    @Mapping(target = "hashPassword", source = "rawPassword", qualifiedByName = "hashPassword")
    UserEntity toEntity(SignUpRequest signUpRequest);

    @Named("hashPassword")
    default String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }
}
