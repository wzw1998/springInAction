[toc]
# 6. spring 的面向切面
## 面向切面编程AOP
### 相关概念
1. 切面（Aspect）： 一个关注点的模块化，这个关注点可能会横切多个对象。事务管理是J2EE应用中一个关于横切关注点的很好的例子。由通知（什么时候，做什么）和切入点（在什么地方）组合而成。
2. 连接点（Joinpoint）： 在程序执行过程中某个特定的点，比如某方法调用的时候或者处理异常的时候。 在Spring AOP中，一个连接点 总是 代表一个方法的执行。 
3. 通知（Advice）： 在切面的某个特定的连接点（Joinpoint）上执行的动作。
4. 切入点（Pointcut）： 匹配连接点（Joinpoint）的断言，也就是定义了切面要切入的连接点。通知和切入点表达式关联，并在满足这个切入点的连接点上运行（例如，当执行某个特定名称的方法时）。 切入点表达式如何和连接点匹配是AOP的核心：Spring缺省使用AspectJ切入点语法。
5. 引入（Introduction）： （也被称为内部类型声明（inter-type declaration））。声明额外的方法或者某个类型的字段。 Spring允许引入新的接口（以及一个对应的实现）到任何被代理的对象。 例如，你可以使用一个引入来使bean实现 IsModified 接口，以便简化缓存机制。
6. 目标对象（Target Object）： 被一个或者多个切面（aspect）所通知（advise）的对象。也有人把它叫做 被通知（advised） 对象。 既然Spring AOP是通过运行时代理实现的，这个对象永远是一个 被代理（proxied） 对象。
7. AOP代理（AOP Proxy）： AOP框架创建的对象，用来实现切面契约（aspect contract）（包括通知方法执行等功能）。 在Spring中，AOP代理可以是JDK动态代理或者CGLIB代理。 注意：Spring 2.0最新引入的基于模式（schema-based）风格和@AspectJ注解风格的切面声明，对于使用这些风格的用户来说，代理的创建是透明的。
8. 织入（Weaving）： 把切面（aspect）连接到其它的应用程序类型或者对象上，并创建一个被通知（advised）的对象。 这些可以在编译时（例如使用AspectJ编译器），类加载时和运行时完成。 Spring和其他纯Java AOP框架一样，在运行时完成织入。<br>

*通知的五种类型*
- 前置通知（Before advice）： 在某连接点（join point）之前执行的通知，但这个通知不能阻止连接点前的执行（除非它抛出一个异常）。
- 返回后通知（After returning advice）： 在某连接点（join point）正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回。
- 抛出异常后通知（After throwing advice）： 在方法抛出异常退出时执行的通知。
- 后通知（After (finally) advice）：  当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。
- 环绕通知（Around Advice）： 包围一个连接点（join point）的通知，如方法调用。这是最强大的一种通知类型。 环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它们自己的返回值或抛出异常来结束执行。

### spring AOP
1. spring AOP是构建在动态代理的基础上，并且该支持仅限于方法拦截。
2. 如图示，代理类封装包含了目标bean，当外部调用了切入点，即目标对象的某个方法，代理类拦截该调用，执行切面逻辑（即通知），再将其转发给目标对象调用方法。（PS：仅支持方法连接点，不提供字段和构造器的接入点）
![image](https://github.com/zhangzexing789/Picture/blob/master/Chapter0401.png?raw=true)
## 通过切点选择连接点
### spring支持的切点指示器
![image](https://github.com/zhangzexing789/Picture/blob/master/Chapter0402.png)
### 编写切点
- 当perform（）方法执行时触发通知<br>
![image](https://github.com/zhangzexing789/Picture/blob/master/Chapter0403.png)
```
execution(* concert.Performance.perform())
```
- 加上限制条件 within() 和 与或判断（&& 、 || ！以及xml使用的 and、or、not）<br>
![image](https://github.com/zhangzexing789/Picture/blob/master/Chapter0404.png)
### 切点中选择bean
- 使用bean()方法，通过bean ID 或者bean 名称来匹配bean
```
execution(* concert.Performance.perform())
    and bean('woodstock')
匹配ID为woodstock的bean
```
## 使用注解
### 定义切面
Performance.java
```
public interface Performance {
    public void perform();
}
```
Audience.java
```
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Audience {

    @Before("execution(* com.zexing.aspectj.Performance.perform(..))")//表演之前手机静音
    public void silenceCellPhones(){
        System.out.println("silencing cell phones");
    }

    @Before("execution(* com.zexing.aspectj.Performance.perform(..))")//表演之前坐好位置
    public void takeSeats(){
        System.out.println("Taking seats");
    }

    @AfterReturning("execution(* com.zexing.aspectj.Performance.perform(..))")//表演成功后鼓掌呐喊
    public void applause(){
        System.out.println("CLAP CLAP CLAP！");
    }

    @AfterThrowing("execution(* com.zexing.aspectj.Performance.perform(..))")//表演失败后要求退款
    public void demandRefun(){
        System.out.println("Demanding a refun");
    }
    
}
```

- @Aspect 

将POJO类定义为一个切面（如Audience类）
- @Before  

目标方法之前执行注解的通知方法（如在perform()之前执行silenCellPhones()和takeSeats()）
- @After 

目标方法返回或抛出异常后执行通知方法
- @AfterReturning

目标方法返回后执行
- @AfterThrowing 

目标方法抛出异常后执行
- @Around 

通知方法将目标方法封装

使用@Pointcut 进行重构，抽出重复的切点表达式

```
    @Pointcut("execution(* com.zexing.aspectj.Performance.perform(..))")
    public void performance(){
    
    }

    @Before("performance()")//表演之前手机静音
    public void silenceCellPhones(){
        System.out.println("silencing cell phones");
    }
```
### 启用自动代理
- java 方式

启动后这里spring容器才会将audience bean 创建为切面
```
@Configuration
@EnableAspectJAutoProxy     //启用自动代理
@ComponentScan
public class ConcertConfig {

    @Bean
    public Audience audience(){
        return new Audience();
    }

}

```
- xml方式
```
    <context:component-scan base-package="com.zexing.aspectj" />

    <aop:aspectj-autoproxy />

    <bean id="audience" class="com.zexing.aspectj.Audience" />
```
AspecJ自动代理都会使用@Aspect注解的bean创建一个代理,而这个代理会围绕着所有该切面的切点所匹配的bean。
### 环绕通知







