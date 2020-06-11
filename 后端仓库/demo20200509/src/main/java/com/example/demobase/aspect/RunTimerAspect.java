package com.example.demobase.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Aspect
@Component
public class RunTimerAspect {

    @Pointcut("@annotation(com.example.demobase.annotation.RunTimer)")
    public void handlingTimePointcut() {}

    @Around("handlingTimePointcut()")
    public Object handlingTimeAround(ProceedingJoinPoint joinPoint){
        try {
            long startTime = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            System.out.println(proceed);
            System.out.println("方法执行时间：" + (System.currentTimeMillis() - startTime)+" 毫秒");
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
