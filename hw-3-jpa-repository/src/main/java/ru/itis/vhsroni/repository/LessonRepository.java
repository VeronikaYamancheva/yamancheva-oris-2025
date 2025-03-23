package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.model.LessonEntity;

import java.util.List;

public interface LessonRepository extends CrudRepository<LessonEntity, Long> {

    Long countLessonsByCourseId(Long courseId);

    void removeLessonFromCourse(Long courseId, Long lessonId);

    List<LessonEntity> findLessonsByCourseId(Long courseId);
}
