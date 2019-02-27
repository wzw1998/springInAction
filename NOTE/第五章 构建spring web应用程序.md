# 构建spring web应用程序
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

模型中会存储一个Spittle列表，key为spittleList，然
后这个列表会发送到名为spittles的视图中。按照我们配
置InternalResourceViewResolver的方式，视图的JSP将会
是“/WEB-INF/views/spittles.jsp”。

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




