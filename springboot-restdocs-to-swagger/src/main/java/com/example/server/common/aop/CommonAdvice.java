package com.example.server.common.aop;

import com.example.server.common.exception.EntityNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonAdvice {

    @Around("execution(public com.example.server..entity.*Entity com.example.server..repository..findByIdx(..))")
    public Object checkFindByIdx(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        if(result == null) {
            throw new EntityNotFoundException();
        }
        return result;
    }

}
