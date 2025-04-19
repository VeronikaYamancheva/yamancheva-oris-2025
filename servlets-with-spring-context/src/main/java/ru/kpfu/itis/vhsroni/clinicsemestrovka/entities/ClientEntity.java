package ru.kpfu.itis.vhsroni.clinicsemestrovka.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String nickname;

    private String password;

    private boolean isAdmin;

    private String photoId;
}
