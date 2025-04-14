package ru.itis.vhsroni;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.itis.vhsroni.model.CourseEntity;
import ru.itis.vhsroni.model.LessonEntity;
import ru.itis.vhsroni.model.UserEntity;
import ru.itis.vhsroni.proxy.TransactionalProxy;
import ru.itis.vhsroni.repository.CourseRepository;
import ru.itis.vhsroni.repository.LessonRepository;
import ru.itis.vhsroni.repository.UserRepository;
import ru.itis.vhsroni.repository.impl.CourseJpaRepositoryImpl;
import ru.itis.vhsroni.repository.impl.LessonJpaRepositoryImpl;
import ru.itis.vhsroni.repository.impl.UserJpaRepositoryImpl;


public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate/hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();

        UserEntity user = UserEntity.builder().name("Fedor").build();

        CourseEntity course = CourseEntity.builder().title("programming").build();

        LessonEntity lesson = LessonEntity.builder().name("oris").course(course).build();

        UserRepository userRepository = new UserJpaRepositoryImpl(entityManager);
        LessonRepository lessonRepository = new LessonJpaRepositoryImpl(entityManager);
        CourseRepository courseRepository = new CourseJpaRepositoryImpl(entityManager);

        UserRepository proxyUserRepository = TransactionalProxy.create(userRepository, entityManager);
        LessonRepository proxyLessonRepository = TransactionalProxy.create(lessonRepository, entityManager);
        CourseRepository proxyCourseRepository = TransactionalProxy.create(courseRepository, entityManager);

        LessonEntity lessonFromDatabase = proxyLessonRepository.save(lesson);
        CourseEntity courseFromDataBase = proxyCourseRepository.save(course);
        UserEntity userFromDatabase = proxyUserRepository.save(user);

        System.out.println("Saved entities: %s, %s, %s".formatted(userFromDatabase, lessonFromDatabase, courseFromDataBase));

        entityManager.close();
        sessionFactory.close();

    }
}