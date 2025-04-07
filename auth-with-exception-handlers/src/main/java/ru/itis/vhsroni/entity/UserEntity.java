package ru.itis.vhsroni.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "account")
public class UserEntity {

    @Id
    @UuidGenerator
    private UUID uuid;

    private String email;

    @Column(name = "hash_password")
    private String hashPassword;

    private String token;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;
}
