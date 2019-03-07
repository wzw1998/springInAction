# 视图解析
## ViewResolver 和 View
- 视图解析器ViewResolver负责处理视图名与实际视图之间的映射关系。 当给resolveViewName()方法传入一个视图名和Locale对象时，它会返回一个View实例.

```java
public interface ViewResolver {
    View resolveViewName(String var1, Locale var2) throws Exception;
}
```
- 视图接口View负责准备请求，并将请求的渲染交给某种具体的视图技术实现。接受模型以及Servlet的request和response对象，并将输出结果渲染到response中。

```java
public interface View {
    String RESPONSE_STATUS_ATTRIBUTE = View.class.getName() + ".responseStatus";
    String PATH_VARIABLES = View.class.getName() + ".pathVariables";
    String SELECTED_CONTENT_TYPE = View.class.getName() + ".selectedContentType";

    String getContentType();

    void render(Map<String, ?> var1, HttpServletRequest var2, HttpServletResponse var3) throws Exception;
}
```
## 视图解析器
- BeanNameViewResolver
将视图解析为Spring应用上下文中的bean，其中
bean的ID与视图的名字相同
- ContentNegotiatingViewResolver
通过考虑客户端需要的内容类型来解析视图，
委托给另外一个能够产生对应内容类型的视图
解析器
- FreeMarkerViewResolver 将视图解析为FreeMarker模板
- InternalResourceViewResolver
将视图解析为Web应用的内部资源（一般为
JSP）
- JasperReportsViewResolver 将视图解析为JasperReports定义
- ResourceBundleViewResolver 将视图解析为资源bundle（一般为属性文件）
- TilesViewResolver
将视图解析为Apache Tile定义，其中tile ID与视
图名称相同。注意有两个不同的TilesViewResolver实现，分别对应于Tiles 2.0和
Tiles 3.0
- UrlBasedViewResolver
直接根据视图的名称解析视图，视图的名称会
匹配一个物理视图的定义
- VelocityLayoutViewResolver
将视图解析为Velocity布局，从不同的Velocity模
板中组合页面
- VelocityViewResolver 将视图解析为Velocity模板
- XmlViewResolver
将视图解析为特定XML文件中的bean定义。类
似于BeanName-ViewResolver
- XsltViewResolver 将视图解析为XSLT转换后的结果
## Jsp 视图
两种方式
- 使用JSP标准标签库
  （JavaServer Pages Standard Tag Library，JSTL），InternalResourceViewResolver能够将视图名解析为
  JstlView形式的JSP文件，从而将JSTL本地化和资源bundle变量暴
  露给JSTL的格式化（formatting）和信息（message）标签。
- 使用spring 提供的两个JSP标签库，一个用于表单到模型的绑定，另一
          个提供了通用的工具类特性。
### jsp 视图解析器的配置
InternalResourceViewResolver 主要是采用拼接的方式，将视图名称拼接到配置的字符串，即添加前缀和后缀，从而确认Web应用中视图资源的物理路径。
- java 配置方式启用mvc 和 定制配置
```java
@Configuration
@EnableWebMvc //启用Spring MVC
@ComponentScan("com.zexing.spittr.web") //启用组件扫描
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ViewResolver viewResolver() {    //配置视图解析器
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
  //```
  
  }
```
- xml 配置方式启用mvc 和 定制配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/mvc"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
  xmlns:beans="http://www.springframework.org/schema/beans"
  xmlns:context="http://www.springframework.org/schema/context" xmlns:mvc="http://www.springframework.org/schema/mvc"
  xsi:schemaLocation="http://www.springframework.org/schema/mvc 
    http://www.springframework.org/schema/mvc/spring-mvc.xsd
    http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context 
    http://www.springframework.org/schema/context/spring-context.xsd">

  <!-- 开启注解驱动的spring mvc-->
  <mvc:annotation-driven /> 

  <context:component-scan base-package="com.zexing.spittr" />

  <beans:bean
    class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <beans:property name="prefix" value="/WEB-INF/views/" />
    <beans:property name="suffix" value=".jsp" />
  </beans:bean>
  
</beans:beans>
```
以上配置解析的是InternalResourceView视图
---
- 配置 Jstl 视图

JSTL的格式化标签需要一个Locale对象，以便于恰当地格式化地域
相关的值，如日期和货币。信息标签可以借助Spring的信息资源和
Locale，从而选择适当的信息渲染到HTML之中。通过解析
JstlView，JSTL能够获得Locale对象以及Spring中配置的信息资
源。

设置它的viewClass属性,即加上以下代码
```
resolver.setViewClass(JstlView.class);
```
而 xml 加上
```xml
 <beans:property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
```
### spring 的JSP库
1.  绑定模型数据的表单标签

在JSP页面声明标签库
```jsp
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
```
- \<sf:checkbox>
渲染成一个HTML \<input>标签，其中type属性设置
为checkbox
- \<sf:checkboxes>
渲染成多个HTML \<input>标签，其中type属性设置
为checkbox
- \<sf:errors> 在一个HTML \<span>中渲染输入域的错误
- \<sf:form> 渲染成一个HTML \<form>标签，并为其内部标签暴露绑定路
径，用于数据绑定
- \<sf:hidden> 渲染成一个HTML \<input>标签，其中type属性设置为hidden
- \<sf:input> 渲染成一个HTML \<input>标签，其中type属性设置为text
- \<sf:label> 渲染成一个HTML \<label>标签
- \<sf:option> 渲染成一个HTML \<option> 标签，其selected属性根据所绑定的值进行设置
- \<sf:options> 按照绑定的集合、数组或Map，渲染成一个HTML \<option>标
签的列表
- \<sf:password>
渲染成一个HTML \<input>标签，其中type属性设置
为password
- \<sf:radiobutton> 渲染成一个HTML \<input>标签，其中type属性设置为radio
- \<sf:select> 渲染为一个HTML \<select>标签
- \<sf:textarea> 渲染为一个HTML \<textarea>标签
---
就Spitter样例,用到注册表单
```jsp
<sf:form method="POST" comandName="spitter">
  First Name: <sf:input path="firstName" /><br/>
  Last Name: <sf:input path="lastName" /><br/>
  Email: <sf:input path="email" /><br/>
  Username: <sf:input path="username" /><br/>
  Password: <sf:password path="password" /><br/>
  <sf:input type="submit" value="Register" />
</sf:form>
```
其中sf:form会渲染一个HTMl 标签，同时通过commandName属性构建针对某个模型对象的上下文信息。
因此在模型中必须要有一个key为Spitter对象，否则的话，表单不能正常渲染(会出现JSP错误).这意味着我们需要修改SpitterController，以确保模型中存在以Spitter为key的Spitter对象。
```
@RequestMapping(value = "/register",method = RequestMethod.GET)
    public String showRegistrationForm(Model model){
        model.addAttribute(new Spitter());//对应registerForm.jsp中 <sf:form> 的commandName属性值
        return "registerForm";
    }
```
我们在这里设置了path属性，< input>标签的value属性值将会设置为spitter对象中path属性所对应的值。

例如在模型中Spitter对象中firstName属性值为guo，那么<sf:input path="firstname"/>所渲染的< input>标签中，会存在value=“guo”。

当用户注册失败后，返回表单将预设先前用户的填写内容

---

从Spring 3.1开始，\<sf:input>标签能够允许我们指
定type属性，即还能指定HTML 5
特定类型的文本域，如date、range和email。例如，我们可以按
照如下的方式指定email域：
```jsp
Email: <sf:input path="email" type="email"/> <br/>
```
2. 向用户展现后台校验的错误信息

如果存在校验错误的话，请求中会包含错误的详细信息，这些信息是
与模型数据放到一起的。我们所需要做的就是到模型中将这些数据抽
取出来，并展现给用户
```jsp
<sf:errors path="firstName"/>
```
path属性指定了将要显示模型对象的哪个属性的错误信息，当校验错误，将会在一个HTML <span>标签中显示错误信息，否则不渲染任何内容。

如以上校验失败时渲染的内容是：
```jsp
<span id="firstName.errors">size must be between 2 and 30</span>
```
1. 用property文件定义错误信息

在校验注解上设置message属性，使其引
          用对用户更为友好的信息。（PS：没有使用{}将直接显示）
          
Spitter.java
```
    @NotNull
    @Size(min=5, max=16, message="{username.size}")
    private String username;
```

ValidationMessages.properties

```
firstName.size=First name must be between {min} and {max} characters long.
```
{min}和{max}会引用@Size注解上所设
置的min和max属性。
### Spring 的通用标签库
声明标签库
```jsp
<%@ taglib uri="http://www.springframework.org.tags" prefix='s'%>
```
---
几种标签说明
- \<s:escapeBody> 将标签体中的内容进行HTML和/或JavaScript转义
- \<s:htmlEscape> 为当前页面设置默认的HTML转义值
- \<s:message>
  根据给定的编码获取信息，然后要么进行渲染（默认行
  为），要么将其设置为页面作用域、请求作用域、会话作用
  域或应用作用域的变量（通过使用var和scope属性实现）
 - \<s:url>
   创建相对于上下文的URL，支持URI模板变量以及
   HTML/XML/JavaScript转义。可以渲染URL（默认行为），也
   可以将其设置为页面作用域、请求作用域、会话作用域或应
   用作用域的变量（通过使用var和scope属性实现）

---
1. 使用 \<s:message> 对展示信息进行国际化处理
- 修改代码
```jsp
<h1><s:message code="spittr.welcome" /></h1>
```
- 配置信息源
```java
@Bean
  public MessageSource messageSource () {   //配置信息源
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages");
    messageSource.setCacheSeconds(10);
    return messageSource;
  }
```
basename属性可以设置为在类路径下（以“classpath:”作
为前缀）、文件系统中（以“file:”作为前缀）或Web应用的根路径
下（没有前缀）查找属性
- 创建property文件

messages.properties
```properties
spittr.welcome=Welcome to Spitter!
```
2.使用\<s:url> 创建URL
- 简单使用
```jsp
 <a href=" <s:url value="/spitter/register" />">register</a>
```
\<s:url> 会接受一个相对于Servlet上下文的URL，并在渲染的时候，在URL 后面拼接上Servlet上下文路径，即当应用的Servlet上下文名为spittr，渲染后的
标签内容是
```jsp
<a href="/spittr/spitter/register">register</a>
```
- 将 URL 赋值变量
```jsp
<s:url value="/spitter/register" var="registerUrl" />

 
<a href=" ${registerUrl}">register</a>
```
- 添加作用域属性
```jsp
<s:url value="/spitter/register" var="registerUrl" scope="page"/>
```
- URL上添加参数
```jsp
<s:url value="/spittles" var="spittlesUrl">
        <s:param name="max" value="60"/>
        <s:param name="count" value="20" />
</s:url>
<a href="${spittlesUrl}">Spittles</a> 
```
跳转的URl将会拼接参数：
```
http://localhost:8080/spittles?max=60&count=20
```
- 渲染URL内容(需要去除 var 属性)

设置属性 htmlEscape 为true
```jsp
<s:url value="/spittles"  htmlEscape="true">
        <s:param name="max" value="60"/>
        <s:param name="count" value="20" />
</s:url>
```
将在页面直接显示URL的内容
```
/spittles?max=60&count=20
```
在javascript里面使用

设置属性 javaScriptEscape 为true
```jsp

    <s:url value="/spittles" var="jsUrl" javaScriptEscape="true">
      <s:param name="max" value="60"/>
      <s:param name="count" value="20" />
    </s:url>

    <script>
      var spittlesUrl = ${jsUrl};
    </script>
```
## 使用Apache Tiles视图定义布局




