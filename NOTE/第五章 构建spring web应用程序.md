[toc]
# 构建spring web应用程序
## Spring MVC
![请求到响应的流程图](https://github.com/zhangzexing789/Picture/blob/master/SpringInAction/Chapter05/Chapter05_01_%E8%AF%B7%E6%B1%82%E5%88%B0%E5%93%8D%E5%BA%94%E7%9A%84%E6%B5%81%E7%A8%8B%E5%9B%BE.png)
- 浏览器发送请求
- spring 的 `DispatcherServlet`（前端控制器）接收请求
- `DispatcherServlet` 根据控制器映射转发请求到控制器处理（实际上，设计良好的控制器本身只处理很少甚至不处理工作，而是将业务逻辑委托给一个或多个服务对象进行处理）
- 控制器将生成模型和逻辑视图名返回给 `DispatcherServlet` （模型是请求信息处理后需要返回到用客户端页面显示的信息）
- `DispatcherServlet` 发送视图名到视图解析器解析成对应的视图文件
- `DispatcherServlet` 使用模型渲数据染视图，作为响应返回
## Spring MVC的核心DispatcherServlet
### 作用
路由请求到其他组件进行处理
### 配置 DispatcherServlet
- 底层原理
Servlet3.0 中，容器首先查找`javax.servlet.ServletContainerInitializer`接口的实现类，用它来配置Servlet容器，也就是spring提供的 `SpringServletContainerInitializer`类，
在这个类中调用 `WebApplicationInitializer` 接口的实现类来完成配置任务。所以到头来只要实现`WebApplicationInitializer`接口即可，spring 就提供了实现类 `AbstractAnnotationConfigDispatcherServletInitializer`，
我们最后仅继承并实现其方法就行.因此当部署到Servlet 3.0容器中的时候，容器会自动发现它，并用它来配置Servlet上下文


- 只要是`AbstractAnnotationConfigDispatcherServletInitializer` 的继承类，即`SpitterWebInitializer`类会自动地配置`DispatcherServlet`和Spring应用上下文，Spring的应用上下文会位于应用程序的Servlet上下文之中。

```java
import com.zexing.spittr.web.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  
    @Override
      protected String[] getServletMappings() { //将DispatcherServlet映射到"/"
    
        return new String[] { "/" };
      }
      
      @Override
      protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
      }
    
      @Override
      protected Class<?>[] getServletConfigClasses() {  //指定配置类
    
        return new Class<?>[] { WebConfig.class };
      }

}
```
- `AbstractAnnotationConfigDispatcherServletInitializer`会同时创建`DispatcherServlet`和`ContextLoaderListener`,并加载配置文件或配置类中所声明的bean。
- getServletMappings()<br>
将一个或多个路径映射到`DispatcherServlet`上。在本例中，它映射的是“/”，这表示它会是应用的默认Servlet。它会处理进入应用的所有请求。
- getServletConfigClasses()<br>
返回带有`@Configuration`注解的类（`WebConfig`），使得前端控制器`DispatcherServlet`加载应用上下文时，使用定义在`WebConfig`配置类（使用Java配置）中的bean,即加载包含Web组件的bean，如控制器、视图解析器以及处理器映射
- getRootConfigClasses()<br>
返回的带有`@Configuration`注解的类(`RootConfig`),使得Servlet监听器`ContextLoaderListener` 加载应用中的其他bean（通常是驱动应用后端的中间层和数据层组件）
- 还可以使用 web.xml 的配置方式
- 这种方式只能部署到支持Servlet 3.0的服务器中才能正常工作，如Tomcat 7或更高版本
### 启用Spring MVC
- 使用\<mvc:annotation-driven>启用注解驱动
- 使用注解`@EnableWebMvc`
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
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) { 
    configurer.enable();
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {   
    super.addResourceHandlers(registry);
  }

}
```
configureDefaultServletHandling()<br>
要求DispatcherServlet将对静态资源的请求转发到Servlet容器中默认的Servlet上，而不是使用DispatcherServlet本身来处理此类请求。

- RootConfig.java
```java
@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackages={"com.zexing.spittr"},
    excludeFilters={
        @Filter(type=FilterType.CUSTOM, value= RootConfig.WebPackage.class)
    })
public class RootConfig {
  public static class WebPackage extends RegexPatternTypeFilter {
    public WebPackage() {
      super(Pattern.compile("spittr\\.web"));
    }    
  }
}
```
## 编辑基本的控制器
### 简单控制器
HomeController.java
```java
@Controller//声明一个控制器
@RequestMapping({"/","/homepage"})
public class HomeController {

  @RequestMapping(method = GET)//处理get请求
  public String home(Model model) {
    return "home";
  }

}
```
当收到对“/”的HTTP GET请求时，就会调用home()方法，返回“home”，而我们配置的视图解析器将会解析成“/WEB-INF/views/home.jsp”路径的JSP。

测试类HomeController.java

```java
public class HomeControllerTest {

    @Test
    public void testHomePage() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        MvcResult result = mockMvc.perform(get("/homepage"))
                .andExpect(view().name("home"))     //验证viewName 
                .andExpect(status().is(200))        //验证状态码 
                .andDo(MockMvcResultHandlers.print())       //输出MvcResult到控制台
                .andReturn();

    }
}
```
### 传递模型到视图

组件类Spittle.java

```java
public class Spittle {
    /**
     *
     * Created by Joson on 2/27/2019.
     */
    private final Long id;
    private final String message;
    private final Date time;
    private Double latitude;
    private Double longitude;

    public Spittle(String message, Date time) {
        this(null, message, time, null, null);
    }

    public Spittle(Long id, String message, Date time, Double longitude, Double latitude) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "id", "time");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id", "time");
    }

}
```

repository类 JdbcSpittleRepository.java

```java
@Repository
public class JdbcSpittleRepository implements SpittleRepository {

    /**
     *
     * Created by Joson on 2/27/2019.
     */
    @Override
    public List<Spittle> findSpittles(Long max, int count) {
        return null;
    }

}
```

controller类 SpittleController.java

```java
@Controller
@RequestMapping("/spittles")
public class SpittleController {
    /**
     *
     * Created by Joson on 2/27/2019.
     */
    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(SpittleRepository spittleRepository){

        this.spittleRepository = spittleRepository;
    }

    /*
    第一种方案： 使用 Model
    @RequestMapping(method = RequestMethod.GET)
    public String splittles(Model model){
        model.addAttribute(spittleRepository.findSpittles(Long.MAX_VALUE,20));  //将返回的spittle list添加到模型中去。
        return "spittles";  //返回spittles作为视图的名字，这个视图会渲染模型。
    }
    Model 实质上是一个Map集合（即key-value），当不指定key值，那么key会
    根据值的对象类型推断确定，如下面例子的键将会推断为spittleList。

    第二种方案：用 Map 代替 Model

    public String splittles(Map model){
        model.put("spittleList",spittleRepository.findSpittles(Long.MAX_VALUE,20));
        return "spittles";
    }
    第三种方案：
    当处理器方法像这样返回对象或集合时，这个值会放到模型中，模型的key会根据其
    类型推断得出（在本例中，也就是spittleList）。
    而逻辑视图的名称将会根据请求路径推断得出。因为这个方法处理针
    对“/spittles”的GET请求，因此视图的名称将会是spittles（去掉开头的斜线）。
     */

    @RequestMapping(method = RequestMethod.GET)
    public List<Spittle> splittles(){
        return spittleRepository.findSpittles(Long.MAX_VALUE,20);
    }

}
```

模型中会存储一个Spittle列表，key为spittleList，然后这个列表会发送到名为spittles的视图中。按照我们配置InternalResourceViewResolver的方式，视图的JSP将会是“/WEB-INF/views/spittles.jsp”。

测试类 SpittleControllerTest.java

```java
public class SpittleControllerTest {

    @Test
    public void shouldShowRecentSpittles() throws Exception {

        List<Spittle> expectedSpittles = createSpittles(20);

        //这里不关心findSpittles()方法的实现原理，只需要其返回结果，所以用mock
        SpittleRepository mockSpittleRepository = mock(SpittleRepository.class);
        when(mockSpittleRepository.findSpittles(anyLong(),anyInt())).thenReturn(expectedSpittles);

        SpittleController spittleController = new SpittleController(mockSpittleRepository);

        //mock springMVC
        MockMvc mockMvc = standaloneSetup(spittleController).setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp"))
                .build();

        //发起请求
        mockMvc.perform(get("/spittles"))
                .andExpect(view().name("spittles"))     //断言视图的名称为spittles
                .andExpect(model().attributeExists("spittleList"))  //断言模型中包含名为spittleList的属性
                .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())))  //断言spittleList的属性包含期望的expectedSpittles
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    private List<Spittle> createSpittles(int count) {

        List<Spittle> spittles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Spittle spittle = new Spittle("Spittle"+i,new Date());
            spittles.add(spittle);
        }
        return spittles;
    }
}
```
需要注意的是，与HomeController不同，这个测试在MockMvc构
造器上调用了setSingleView()。这样的话，mock框架就不用解析
控制器中的视图名了。在很多场景中，其实没有必要这样做。但是对
于这个控制器方法，视图名与请求路径是非常相似的，这样按照默认
的视图解析规则时，MockMvc就会发生失败，因为无法区分视图路
径和控制器的路径。在这个测试中，构建InternalResourceView
时所设置的实际路径是无关紧要的，但我们将其设置为
与InternalResourceViewResolver配置一致。

## 接受请求的输入
### 处理查询参数

controller类 SpittleController.java

```java
public class SpittleController{
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Spittle> spittles(  //当参数不存在使用默认值
            @RequestParam(value = "max",defaultValue = MAX_LONG_AS_STRING) long max,
            @RequestParam(value = "count",defaultValue = "20") int count) {

        return spittleRepository.findSpittles(max,count);
    }
}
    
```

测试类 SpittleControllerTest.java

```java
public class SpittleControllerTest{
    
    @Test
    public void shouldShowPagedSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittles(50);
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(238900L, 50))      //预期的max 和count 参数
                .thenReturn(expectedSpittles);

        SpittleController controller = new SpittleController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc.perform(get("/spittles?max=238900&count=50"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
```

### 通过路径参数接受输入
```java
public class Spittle{
    
        @RequestMapping(value = "/{spittleId}",method = RequestMethod.GET)
        public String spittle (
                @PathVariable long spittleId,Model model){
            model.addAttribute(spittleRepository.findOneSpittle(spittleId)) ;
            return "spittle";
        }
}

```

- 使用传统带参数的请求"/spittles/show?spittleId=12345” 用@RequestParam(value = "spittleId"）

- 使用占位符 "/spittles/{spittleId}" 用@PathVariable，而且当方法的参数名碰巧与占位符的名称相同，可以省略其value属性

- @pathVariable("id")          识别URL里面的模板,即需要配合rest风格url使用    http://127.0.0.1:8082/id/4

- @RequestParam（value="id"）   接收普通？后面携带的参数        http://127.0.0.1:8082/hello?id=4


测试类 SpittleControllerTest.java

```java
public class SpittleControllerTest{
    
    @Test
        public void testSpittle() throws Exception {
    
            Spittle expectedSpittle = new Spittle("hello",new Date());
            SpittleRepository mockSpittleRepository = mock(SpittleRepository.class);
    
            when(mockSpittleRepository.findOneSpittle(12345)).thenReturn(expectedSpittle);
    
            SpittleController controller = new SpittleController(mockSpittleRepository);
            MockMvc mockMvc = standaloneSetup(controller).build();
    
            ResultActions resultActions = mockMvc.perform(get("/spittles/12345"));
            resultActions.andExpect(view().name("spittle"));
            resultActions.andExpect(model().attributeExists("spittle"));
            resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
        }
}
```
## 处理表单
### 获取表单
SpillerController.java
```java

@Controller
@RequestMapping("/spitter")
public class SpitterController {
    /**
     *
     * Created by Joson on 2/28/2019.
     */
    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(SpitterRepository spitterRepository){
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String showRegistrationForm(){
        return "registerForm";
    }
}
```
SpitterControllerTest.java
```java
public class SpitterControllerTest {


    @Test
    public void showRegistrationForm() throws Exception {

        SpitterRepository mockSpitterRepository = mock(SpitterRepository.class);

        SpitterController spitterController = new SpitterController(mockSpitterRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spitterController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spittler/register"))
                .andExpect(MockMvcResultMatchers.view().name("registerForm"));
    }
}
```
### 处理提交的表单
SpillerController.java

```java
	@RequestMapping(value = "/register",method = RequestMethod.POST)
    public String processRegistration(@Valid Spitter spitter,Errors errors){
        if (errors.hasErrors()) {
            return "registerForm";
        }
        spitterRepository.save(spitter);
        return "redirect:/spitter/"+ spitter.getUsername(); //重定向到个人信息页面，避免表单重复提交
    }
```

InternalResourceViewResolver 接收到视图格式中的“redirect:”前缀时，它将其解析为重定向的规则；视图格式中以“forward:”作为前缀时，请求将会前往（forward）指定的URL路径

SpitterControllerTest.java

```java
	@Test
    public void  processRegistration() throws Exception {

        Spitter unSavedSpitter = new Spitter("Joson","12345","zhang","jacoo","12345@168.com");
        Spitter savedSpitter = new Spitter(10L,"Joson","12345","zhang","jacoo","12345@168.com");

        SpitterRepository mockSpitterRepository = mock(SpitterRepository.class);
        when(mockSpitterRepository.save(unSavedSpitter)).thenReturn(savedSpitter);

        SpitterController spitterController = new SpitterController(mockSpitterRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spitterController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/spittler/register")
                .param("username","Joson")
                .param("password","12345")
                .param("firstName","zhang")
                .param("lastName","jacoo")
                .param("email","12345@168.com"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/spittler/Joson"));

    }
```

### 重定向到的个人信息的请求处理

SpitterController.java
```java
	@RequestMapping(value = "/profile/{userName}")
    public String showSpitterProfile(@PathVariable String userName, Model model){   //这里展现user的信息，需要模型数据传递给视图
        model.addAttribute(spitterRepository.findSpitterByUserName(userName));
        return "profile";
    }
```

SpitterControllerTest.java

```java
	@Test
    public void showSpitterProfile() throws Exception {
        Spitter savedSpitter = new Spitter(10L,"Joson","12345","zhang","jacoo","12345@168.com");

        SpitterRepository mockSpitterRepository = mock(SpitterRepository.class);
        when(mockSpitterRepository.findSpitterByUserName(savedSpitter.getUsername())).thenReturn(savedSpitter);

        SpitterController spitterController = new SpitterController(mockSpitterRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spitterController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/spittler/profile/Joson"))
                .andExpect(MockMvcResultMatchers.view().name("profile"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spitter"));
    }
```

### 使用Java Validation API 表单检验
pom.xml
```xml
<!-- https://mvnrepository.com/artifact/javax.validation/validation-api -->
        <dependency>
            <groupId>javax.validation</groupId>
            <artifactId>validation-api</artifactId>
            <version>2.0.1.Final</version>
        </dependency>
        <!-- 需要加上下面的依赖，否则以下API 会不起作用
        书中原话：只要保证在类路径下包含这个Java API的实现即可，比如Hibernate Validator。 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>5.1.1.Final</version>
        </dependency>
```
[SpringMVC @Valid无效 解决方案](https://blog.csdn.net/miaoch/article/details/81511348）)

提供的检验注解
- @AssertFalse 所注解的元素必须是Boolean类型，并且值为false
- @AssertTrue 所注解的元素必须是Boolean类型，并且值为true
- @DecimalMax 所注解的元素必须是数字，并且它的值要小于或等于给定的BigDecimalString值
- @DecimalMin 所注解的元素必须是数字，并且它的值要大于或等于给定的 BigDecimalString值
- @Digits 所注解的元素必须是数字，并且它的值必须有指定的位数
- @Future 所注解的元素的值必须是一个将来的日期
- @Max 所注解的元素必须是数字，并且它的值要小于或等于给定的值
- @Min 所注解的元素必须是数字，并且它的值要大于或等于给定的值
- @NotNull 所注解元素的值必须不能为null
- @Null 所注解元素的值必须为null
- @Past 所注解的元素的值必须是一个已过去的日期
- @Pattern 所注解的元素的值必须匹配给定的正则表达式
- @Size
  所注解的元素的值必须是String、集合或数组，并且它的长度要符
  合给定的范围
---
Spitter.java
```java
public class Spitter {
    /**
     *
     * Created by Joson on 2/28/2019.
     */

    private Long id;

    @NotNull            //非空
    @Size(min=5, max=16)//5-16个字符
    private String username;

    @NotNull
    @Size(min=5, max=25)
    private String password;

    @NotNull
    @Size(min=2, max=30)
    private String firstName;

    @NotNull
    @Size(min=2, max=30)
    private String lastName;

    @NotNull
    @Email
    private String email;
    
}
```
SpillerController.java

```java
@RequestMapping(value = "/register",method = RequestMethod.POST)
    public String processRegistration(@Valid Spitter spitter,Errors errors){ //检验spitter 输入
        if (errors.hasErrors()) {
            return "registerForm";  //检验错误将返回 registerForm 表单
        }
        spitterRepository.save(spitter);
        return "redirect:/spitter/"+ spitter.getUsername(); 
    }
```
> Spitter参数添加了@Valid注解，这会告知 Spring，需要确保这个对象满足校验限制。

> 在Spitter属性上添加校验限制并不能阻止表单提交。即便用户没有填写某个域或者某个域所给定的值超出了最大长度，processRegistration()方法依然会被调用。这样，我们就需要处理校验的错误，就像processRegistration()方法中所看到的那样。
> 如果有校验出现错误的话，那么这些错误可以通过Errors对象进行 访问，现在这个对象已作为processRegistration()方法的参数。（PS: Errors参数要紧跟在带有@Valid注解的参数后面，@Valid注解所标注的就是要检验的参数。)
> 如果有错误的话，Errors.hasErrors()将会返回到registerForm，也就是注册表单的视图。这能够让用户的浏览
器重新回到注册表单页面，所以他们能够修正错误，然后重新尝试提交；如果没有错误的话，Spitter对象将会通过Repository进行保存，控制器会像之前那样重定向到基本信息页面。






