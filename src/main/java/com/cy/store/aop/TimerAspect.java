package com.cy.store.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect    //当前类记为切面类
@Component //放入ioc容器
public class TimerAspect {
//  表示该方法映射到哪个切面
// execution(* com.cy.store.service.impl.*.*(..))
//   第一个*表示返回值类型
//  com.cy.store.service.impl.*.*(..)表示具体的方法, ..表示所有参数
    @Around("execution(* com.cy.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //ProceedingJoinPoint表示目标方法的对象
        //proceed()为调用目标方法
        //记录开始时间
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println("耗时 = " + (end - start));

        return result;
    }
}
