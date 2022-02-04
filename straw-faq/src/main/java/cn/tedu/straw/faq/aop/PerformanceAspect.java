package cn.tedu.straw.faq.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Pointcut("execution(* cn.tedu.straw.faq.service.*Service.*(..))")
    public void servicePointCut() {
    }

    ;

    @Around("servicePointCut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long t1 = System.nanoTime();
        Object val = joinPoint.proceed();
        long t2 = System.nanoTime();
        Signature signature = joinPoint.getSignature();
        log.info("{}方法執行耗時{}", signature, (t2 - t1));
        return val;
    }
}
