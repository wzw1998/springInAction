package com.zexing.aspectj;


import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    @Pointcut("execution(* com.zexing.aspectj.Performance.perform(..))")
    public void performance(){
    }

//    @Before("execution(* com.zexing.aspectj.Performance.perform(..))")//表演之前手机静音
    @Before("performance()")
    public void silenceCellPhones(){
        System.out.println("silencing cell phones");
    }

//    @Before("execution(* com.zexing.aspectj.Performance.perform(..))")//表演之前坐好位置
    @Before("performance()")
    public void takeSeats(){
        System.out.println("Taking seats");
    }

//    @AfterReturning("execution(* com.zexing.aspectj.Performance.perform(..))")//表演成功后鼓掌呐喊
    @AfterReturning("performance()")
    public void applause(){
        System.out.println("CLAP CLAP CLAP！");
    }

//    @AfterThrowing("execution(* com.zexing.aspectj.Performance.perform(..))")//表演失败后要求退款
    @AfterThrowing("performance()")
    public void demandRefun(){
        System.out.println("Demanding a refun");
    }

}
