package com.example.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component // Spring bean으로 등록해줘야함. Aop는 Config 파일에 명시하는게 좋음
@Aspect // 이걸 적어줘야 aop로 쓸 수 있음
public class TimeTraceAop {

    @Around("execution(* com.example.hellospring.service..*(..))") // 패키지 내의 모든 클래스, 모든 파라미터 가능하도록
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        System.out.println("Start : " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("End : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
