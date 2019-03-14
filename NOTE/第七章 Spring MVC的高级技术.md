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
