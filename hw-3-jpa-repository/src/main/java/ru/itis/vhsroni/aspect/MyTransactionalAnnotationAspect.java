package ru.itis.vhsroni.aspect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class MyTransactionalAnnotationAspect {

    private final EntityManager entityManager;

    @Pointcut("@annotation(ru.itis.vhsroni.annotation.MyTransactional)")
    public void myTransactionalMethod() {
    }

    @Around("myTransactionalMethod()")
    public Object manageTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Object result = proceedingJoinPoint.proceed();
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }

    }
}
