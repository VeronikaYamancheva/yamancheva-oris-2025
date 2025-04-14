package ru.itis.vhsroni.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
@ToString(exclude = "users")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "course")
    private Set<LessonEntity> lessons;

    @ManyToMany(mappedBy = "courses")
    private Set<UserEntity> users;
}
