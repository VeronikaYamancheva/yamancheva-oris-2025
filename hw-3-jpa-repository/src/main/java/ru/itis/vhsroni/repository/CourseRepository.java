package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.model.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {

    Long countCourses();

    Optional<CourseEntity> findByTitle(String title);

    void addUserToCourse(Long courseId, Long userId);
}
