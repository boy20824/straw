package cn.tedu.straw.gateway.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DemoAdvice {

    @Pointcut("execution(public * cn.tedu.straw.gateway.controller.TestController.test(..))")
    public void pointCut(){};

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        Signature signature=joinPoint.getSignature();
        log.debug("在{}方法之前執行",signature);
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        Signature signature=joinPoint.getSignature();
        log.debug("在{}方法之後執行",signature);
    }

    @AfterReturning("pointCut()")
    public void afterReturning(JoinPoint joinPoint){
        Signature signature=joinPoint.getSignature();
        log.debug("在方法返回值{}之後執行",signature);
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(JoinPoint joinPoint){
        Signature signature=joinPoint.getSignature();
        log.debug("在方法出現異常{}之後執行",signature);
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature=joinPoint.getSignature();
        log.debug("Around 在方法{}之前執行",signature);
        //只有執行proceed()才會執行被切入的最終方法
        Object val=joinPoint.proceed();
        log.debug("Around 在方法{}之後執行",signature);

        return val;
    }
}