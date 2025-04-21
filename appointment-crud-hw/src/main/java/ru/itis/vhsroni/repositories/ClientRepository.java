package ru.itis.vhsroni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vhsroni.data.entities.ClientEntity;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    Optional<ClientEntity> findByUser_Email(String email);
}
