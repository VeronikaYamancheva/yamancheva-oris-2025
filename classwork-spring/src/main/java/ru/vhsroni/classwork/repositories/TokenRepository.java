package ru.vhsroni.classwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vhsroni.classwork.entity.TokenEntity;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByTokenValue(String tokenValue);
    void deleteByTokenValue(String tokenValue);
    void deleteByUserId(Long userId);
}
