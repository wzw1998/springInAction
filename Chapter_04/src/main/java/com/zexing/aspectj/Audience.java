package com.zexing.aspectj;


import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    @Pointcut("execution(* com.zexing.aspectj.Performance.perform(..))")
    public void performance(){
    }

    @Before("performance()")//表演之前手机静音
    public void silenceCellPhones(){
        System.out.println("silencing cell phones");
    }

    @Before("performance()")//表演之前坐好位置
    public void takeSeats(){
        System.out.println("Taking seats");
    }

    @AfterReturning("performance()")//表演成功后鼓掌呐喊
    public void applause(){
        System.out.println("CLAP CLAP CLAP！");
    }

    @AfterThrowing("performance()")//表演失败后要求退款
    public void demandRefun(){
        System.out.println("Demanding a refun");
    }

}
