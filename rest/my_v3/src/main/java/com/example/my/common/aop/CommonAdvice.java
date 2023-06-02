package com.example.my.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.my.common.exception.EntityNotFoundException;

@Aspect
@Component
public class CommonAdvice {
    @Around("execution(public com.example.my..entity.*Entity com.example.my..repository..findByIdx(..))")
    public Object checkFindByIdx(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {


        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        if(result == null) {
            throw new EntityNotFoundException();
        }
        return result;
    }

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
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof CustomUserDetails){
//            System.out.println("assdfasdfasdf");
//        }
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        log.info("접속자 IP : " + request.getRemoteAddr());
//
//        return proceedingJoinPoint.proceed();
//    }

}
