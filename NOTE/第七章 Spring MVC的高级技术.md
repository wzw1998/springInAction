[toc]
# Spring MVC配置的替代方案
## 自定义DispatcherServlet配置
除了之前重载三种方法实现配置前端处理器`DispatcherServlet`的映射和需要加载的配置类，以及Servlet监听器`ContextLoaderListener`需要加载的根配置类外，还需要需要额外的Servlet和Filter，这时就要`SpitterWebInitializer`
添加更多的重载方法来实现。

```
  @Override  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
          registration.setMultipartConfig(          //启用对multipart请求的支持
          new MultipartConfigElement("/tmp/spittr/uploads")
          );
          }
```
借助customizeRegistration()方法中的ServletRegistration.Dynamic我们能够完成更多的任务

- 调用setLoadOnstartup()设置load-on-startup 优先级；
- 通过setInitParameter()设置初始化参数；
- 通过调用setMultipartConfig()配置Servlet3.0对multipart的支持。

## 添加额外的servlet 和 filter
- 创建servlet
创建一个初始化类实现`WebApplicationInitializer`,并注册一个servlet
```java
public class MyServletInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
      Dynamic  myServlet = servletContext.addServlet("myServlet",myServlet.class);      //注册servlet
      myServlet.addMapping("/custom/**");       //映射路径
    }
}
```
- 创建Listener 和 Filter
```
@Override
public void onStartup(ServletContext servletContext) throws ServletException {
    javax.servlet.FilterRegistration.Dynamic filter = servletContext.addFilter("myFilter",myFilter.class);
    filter.addMappingForUrlPatterns(null,false,"/custom/*");
}
```
- 如果只是注册Filter，并且该Filter只会映射到`DispatcherServlet`上,重载`AbstractAnnotationConfigDispatcherServletInitializer`的getServletFilter()方法即可
```
@Override
protected Filter[] getServletFilters() {
    return new Filter[] {new Myfilter()};
}
```
## 使用 web.xml 配置 DispatcherServlet

web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         id="taotao" version="2.5">
  <display-name>appServlet</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
  </welcome-file-list>

  <!--
  根据之前的配置，我们需要手动的创建注册以下bea，并进行配置
  1. 注册 DispatcherServlet
  2. 注册ContextLoaderListener
  3. 配置根上下文xml文件的位置
  4. 映射路径到 DispatcherServlet
  -->

  <!-- 注册 springmvc 前端控制器 -->
  <servlet>
    <servlet-name>appServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <!--配置监听器-->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!-- 加载spring容器 -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <!--设置根上下文配置文件的位置 将由ContextLoaderListener加载-->
    <param-value>classpath:spring/applicationContext-*.xml</param-value>
  </context-param>

  <!--映射路径-->
  <servlet-mapping>
    <servlet-name>appServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
```
`DispatcherServlet`加载应用上下文，默认根据Servlet的名字进行加载，如上面的appServlet，`DispatcherServlet`会从“/WEB-INF/appServlet-context.xml”文件中加载其应用上下文.
否则通过web.xml 进行指定,使用标签`<init-param>`
```xml
<!-- 注册 springmvc 前端控制器 -->
  <servlet>
    <servlet-name>appServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
```

