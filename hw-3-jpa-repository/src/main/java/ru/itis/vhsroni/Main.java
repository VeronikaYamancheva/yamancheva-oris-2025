package ru.itis.vhsroni;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.itis.vhsroni.model.UserEntity;
import ru.itis.vhsroni.repository.UserRepository;
import ru.itis.vhsroni.repository.impl.UserJpaRepositoryImpl;


public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate/hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();

        UserEntity user = UserEntity.builder()
                .name("Fedor")
                .build();

        UserRepository userRepository = new UserJpaRepositoryImpl(entityManager);
        UserEntity userFromDatabase = userRepository.save(user);

        System.out.println(userFromDatabase);
    }
}