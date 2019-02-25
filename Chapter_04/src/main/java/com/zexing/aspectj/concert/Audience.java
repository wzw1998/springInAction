package com.zexing.aspectj.concert;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Audience {

    @Pointcut("execution(* com.zexing.aspectj.concert.Performance.perform(..))")
    public void performance(){
    }

//    @Before("execution(* com.zexing.aspectj.concert.Performance.perform(..))")//表演之前手机静音
    @Before("performance()")
    public void silenceCellPhones(){
        System.out.println("silencing cell phones");
    }

//    @Before("execution(* com.zexing.aspectj.concert.Performance.perform(..))")//表演之前坐好位置
    @Before("performance()")
    public void takeSeats(){
        System.out.println("Taking seats");
    }

//    @AfterReturning("execution(* com.zexing.aspectj.concert.Performance.perform(..))")//表演成功后鼓掌呐喊
    @AfterReturning("performance()")
    public void applause(){
        System.out.println("CLAP CLAP CLAP！");
    }

//    @AfterThrowing("execution(* com.zexing.aspectj.concert.Performance.perform(..))")//表演失败后要求退款
    @AfterThrowing("performance()")
    public void demandRefun(){
        System.out.println("Demanding a refun");
    }

    /*
    环绕通知，实现结果与前置后置通知一样
     */
    @Around("performance()")
    public void watchPerformance(ProceedingJoinPoint jp) {
        try {
            System.out.println("手机静音");
            System.out.println("得到座位");
            jp.proceed();
            System.out.println("鼓掌!!!");
        } catch (Throwable e) {
            System.out.println("这演的啥啊！退票");
        }
    }

}
