package ru.itis.vhsroni.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "message")
public class MessageEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String author;

    private String content;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Column(name = "last_update")
    private Instant lastUpdate;
}
