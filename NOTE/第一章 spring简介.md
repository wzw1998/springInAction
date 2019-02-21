[toc]

# 1. spring 的轻量级入侵性： <br>
- 依赖注入（DI）：通过xml配置，生成bean，注入接口（接收多种实现）

- 面向切面（AOP）：xml配置，定义切面类，切点，前置通知和后置通知。POJO类无需关注切面类，甚至不知道其存在。切面类同样是一个bean。

- 模板的使用：减少样板式代码。JdbcTemplate

# 2. spring 容器 ：创建，装配，配置和管理对象的生命周期 <br>

 （1） bean 工厂（org.springframework.beans.factory.BeanFactory）

 （2） 应用上下文（org.springFramework.context.ApplicationContext）

> - AnnotationConfigApplicationContext  ——从一个或者多个基于Java的配置类加载spring上下文
> - AnnotationConfigWebApplicationContext ——从一个或者多个基于Java的配置类加载spring web上下文
> - ClasPathXmlApplicationContext ——从类路径加载上下文定义
> - FileSystemXmlApllicationContext——从文件系统加载
> - XmlWebApplicationContext ——从web应用

# 3. spring 核心容器
 - Beans
 - Core
 - Context
 - Expression
 - Context support
