package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.model.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {

    @MyTransactional
    Long countCourses();

    @MyTransactional
    Optional<CourseEntity> findByTitle(String title);

    @MyTransactional
    void addUserToCourse(Long courseId, Long userId);
}
