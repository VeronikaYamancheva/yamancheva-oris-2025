package ru.itis.vhsroni.proxy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.annotation.MyTransactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@RequiredArgsConstructor
public class TransactionalProxy<T> implements InvocationHandler {

    private final T target;

    private final EntityManager entityManager;


    @SuppressWarnings("unchecked")
    public static <T> T create(T target, EntityManager entityManager) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TransactionalProxy<>(target, entityManager)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(MyTransactional.class)) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Object result = method.invoke(target, args);
                transaction.commit();
                return result;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
        return method.invoke(target, args);
    }
}
