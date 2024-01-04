package com.example.springintroduction.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect // AOP : 관점 지향적 프로그래밍
// 모든 클래스마다 공통적으로 수행되어야 하는 기능에 대해서
public class TimeTraceAop {

    // com.example.springintroduction 하위 모든 클래스에 공통관심사항 적용
    @Around("execution(* com.example.springintroduction..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }

}
