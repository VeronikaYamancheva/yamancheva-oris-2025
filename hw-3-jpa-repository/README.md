# Домашняя работа 3. JPA репозитории.

### На основе кода с пары были дописаны:
- CourseJpaRepositoryImpl
- LessonJpaRepositoryImpl
- UserJpaRepositoryImpl

### Кастомные методы:
1. CourseJpaRepositoryImpl
- Long countCourses();
- Optional<CourseEntity> findByTitle(String title);
- void addUserToCourse(Long courseId, Long userId);

2. LessonJpaRepositoryImpl 
- Long countLessonsByCourseId(Long courseId);
- void removeLessonFromCourse(Long courseId, Long lessonId);
- List<LessonEntity> findLessonsByCourseId(Long courseId);

3. UserJpaRepositoryImpl 
- Optional<UserEntity> findByName(String name);
- List<UserEntity> findUsersWithNoCourses();
- Long countUsersByCourseId(Long courseId);
- List<UserEntity> findUsersByCourseId(Long courseId);

### Оптимизация (избежание дублирования кода)
- В основе - JDK Dynamic Proxy (основано на интерфейсах => аннотации ставим а методами интерфейсов класса)
- Методы, требующие транзакцию, помечаются аннотацией @MyTransactional
- Далее для корректной обработки транзакций вызываем proxy-объекты

### Тесты
- Протестированы только 3 класса репозитории-реализации (unit, mock)