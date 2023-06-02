package com.example.my.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class CommonAdvice {
//    // 참고
//    // https://icarus8050.tistory.com/8
//    // https://hyuuny.tistory.com/101
//    @Before("execution(* com.example.my..controller..*(..))")
//    @Before("within(com.example.my..controller.*)")
//    @Before("bean(*ControllerApi*)")
//    public void test1() {
//        log.info("test1");
//    }

//    @Around("bean(*ControllerApi*)")
//    public Object checkIP(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        log.info("접속자 IP : " + request.getRemoteAddr());
//
//        return proceedingJoinPoint.proceed();
//    }

    @Around("execution(* com.example.my..repository..findByIdx(..))")
    public Object checkIP(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        log.info(Arrays.toString(args));
        Object result = proceedingJoinPoint.proceed(args);
        if(result == null) {
            throw new EntityNotFoundException();
        }
        return result;
    }

}
