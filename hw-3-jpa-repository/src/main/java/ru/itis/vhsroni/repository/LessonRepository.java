package ru.itis.vhsroni.repository;

import ru.itis.vhsroni.annotation.MyTransactional;
import ru.itis.vhsroni.model.LessonEntity;

import java.util.List;

public interface LessonRepository extends CrudRepository<LessonEntity, Long> {

    @MyTransactional
    Long countLessonsByCourseId(Long courseId);

    @MyTransactional
    void removeLessonFromCourse(Long courseId, Long lessonId);

    @MyTransactional
    List<LessonEntity> findLessonsByCourseId(Long courseId);
}
