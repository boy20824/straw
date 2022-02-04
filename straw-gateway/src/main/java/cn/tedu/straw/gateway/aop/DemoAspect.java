package cn.tedu.straw.gateway.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class DemoAspect {

    /*
    聲明切入點
    表示AOP組件方法切入到TestController.test(..),再調用test方法時候執行
     */
    @Pointcut("execution(public * cn.tedu.straw.gateway.controller.TestController.test(..))")
    public void pointCut(){};

    /*
    在..之前執行
     */
    @Before("pointCut()")
    public void before(){
        log.debug("Before test()");
    }
}
