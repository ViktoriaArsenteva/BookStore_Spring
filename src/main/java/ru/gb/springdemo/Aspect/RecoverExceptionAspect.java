package ru.gb.springdemo.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RecoverExceptionAspect {

    @Pointcut("@annotation(recoverException)")
    public void recoverExceptionPointcut(RecoverException recoverException) {
    }

    @AfterThrowing(value = "recoverExceptionPointcut(recoverException)", throwing = "ex")
    public void recoverException(RecoverException recoverException, RuntimeException ex) {
        for (Class<? extends RuntimeException> exceptionClass : recoverException.noRecoverFor()) {
            if (exceptionClass.isInstance(ex)) {
                throw ex; // Rethrow the exception if it matches the noRecoverFor list
            }
        }
        // Handle the exception here by returning a default value
        System.out.println("Recovering from exception: " + ex.getMessage());
    }
}