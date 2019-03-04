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
## 