package ru.itis.vhsroni.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "users")
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    @ManyToMany(mappedBy = "courses")
    private List<UserEntity> users;
}
