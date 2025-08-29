package com.springbook.biz.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect // Aspect = Pointcut + Advice
public class BeforeLogAdvice {

    @Pointcut("execution(* com.springbook.biz..*Impl.*(..))")
    public void allPointcut() {}

    @Pointcut("execution(* com.springbook.biz..*Impl.get*(..))")
    public void getPointcut() {}

    public void printLog(JoinPoint jp) {
        System.out.println("[공통 로그] 비즈니스 로직 수행 전 동작");
    }

    @Before("allPointcut()")
    public void beforeLog(JoinPoint jp) {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();

        System.out.println("[사전 처리]" + method + "() 메소드 ARGS 정보 : "+ args[0].toString());
    }
}
