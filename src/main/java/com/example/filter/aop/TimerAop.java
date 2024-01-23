package com.example.filter.aop;

import com.example.filter.model.UserRequest;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {

    @Pointcut(value = "within(com.example.filter.controller.UserApiController)") // 실행하고자 하는 포인트컷의 위치 지정 (스프링 빈에만 지정 가능)
    public void timerPointCut() {}

    // 메소드 실행 전
    @Before(value = "timerPointCut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("before");
    }

    // 메소드 실행 후
    @After(value = "timerPointCut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("after");
    }

    // 메소드 실행이 성공적일 때
    @AfterReturning(value = "timerPointCut()", returning ="result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("after returning");
    }

    // 메소드 실행 중 예외가 발생했을 때
    @AfterThrowing(value = "timerPointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("after throwing");
    }

    @Around(value = "timerPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("메소드 실행 이전");
        Arrays.stream(joinPoint.getArgs()).forEach(
                it -> {
                    System.out.println("it = " + it);
                    if (it instanceof UserRequest) {
                        var tempUser = (UserRequest) it;
                        var newPhoneNumber = tempUser.getPhoneNumber().replace("-", "");
                        tempUser.setPhoneNumber(newPhoneNumber);
                    }
                }
        );
        // 보통 암/복호화 혹은 로깅을 사용함
        var newObjs = Arrays.asList(
                new UserRequest()
        );

        // 메소드 실행 시간 확인하는 법
        var stopWatch = new StopWatch();

        stopWatch.start();
//        joinPoint.proceed();
        joinPoint.proceed(newObjs.toArray());
        stopWatch.stop();
        System.out.println("총 소요된 시간(ns) = " + stopWatch.getTotalTimeNanos());

        System.out.println("메소드 실행 이후");
    }
}
