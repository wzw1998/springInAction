@[toc]
# spring 的面向切面
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
![spring AOP 原理](https://img-blog.csdnimg.cn/20190306224221878.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNDU2NTk3Nw==,size_16,color_FFFFFF,t_70)
## 通过切点选择连接点
### spring支持的切点指示器
![切点指示器](https://img-blog.csdnimg.cn/20190306224338671.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNDU2NTk3Nw==,size_16,color_FFFFFF,t_70)
### 编写切点
- 当perform（）方法执行时触发通知<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190306224658686.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNDU2NTk3Nw==,size_16,color_FFFFFF,t_70)
```java
execution(* concert.Performance.perform())
```
- 加上限制条件 within() 和 与或判断（&& 、 || ！以及xml使用的 and、or、not）<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190306224716669.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNDU2NTk3Nw==,size_16,color_FFFFFF,t_70)
### 切点中选择bean
- 使用bean()方法，通过bean ID 或者bean 名称来匹配bean
```java
execution(* concert.Performance.perform())
    and bean('woodstock')
匹配ID为woodstock的bean
```
## 使用注解
### 目标对象
接口 Performance.java
```java
public interface Performance {
    public void perform();
}
```
目标类 Concert.java
```java
@Component
public class Concert implements Performance {

    public void perform() {
        System.out.println("Performing...");
    }

}
```


### 定义切面

Audience.java
```java
@Component
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

可以使用@Pointcut 进行重构，抽出重复的切点表达式

```java
    @Pointcut("execution(* com.zexing.aspectj.Performance.perform(..))")
    public void performance(){
    
    }

    @Before("performance()")//表演之前手机静音
    public void silenceCellPhones(){
        System.out.println("silencing cell phones");
    }
```
### 配置类或者xml文件启用自动代理
- 注解方式

启动后这里spring容器才会将audience bean 创建为切面
```java
@Configuration
@EnableAspectJAutoProxy     //启用自动代理
@ComponentScan
public class ConcertConfig {
}

```
- xml方式
```xml
    <context:component-scan base-package="com.zexing.aspectj" />

    <aop:aspectj-autoproxy />

    <bean id="audience" class="com.zexing.aspectj.Audience" />
```
AspecJ自动代理都会使用@Aspect注解的bean创建一个代理,而这个代理会围绕着所有该切面的切点所匹配的bean。
### 测试
测试类 ConcertConfigTest.java
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
//@ContextConfiguration(locations = "classpath*:app.xml")
public class ConcertConfigTest {

    @Autowired
    private Performance concert;

    @Test
    public void concertStart(){

        assertNotNull(concert);
        concert.perform();
    }
}
```
结果
```jvaa
silencing cell phones
Taking seats
Performing...
CLAP CLAP CLAP！
```
### 环绕通知
Audience.java
```java
@Aspect
public class Audience {

    @Pointcut("execution(* com.zexing.aspectj.Performance.perform(..))")
    public void performance(){
    }
    
    @Around("performance()")
    public void watchPerformance(ProceedingJoinPoint jp) {
        try {
            System.out.println("手机静音");
            System.out.println("得到座位");
            jp.proceed();
            System.out.println("鼓掌!!!");
        } catch (Throwable e) {
            System.out.println("这演的啥啊！退票");
        }
    }
    
}
```
这里的 @Around注解就声明了watchPerformance()方法成为了一个环绕通知切点。他的运行结果与上例使用@Before,@After等等的效果相同。可以发现watchPerformance()方法中给出了一个PreceedingJoinPoint类型的参数，这个是必须的，因为你需要告诉该切点中的任务相对与你的工作所处的位置。使用ProceedingJoinPoint’s proceed()方法将你的任务放在你想要的位置

**测试结果**
```java
手机静音
得到座位
Performing...
鼓掌!!!
```
### 处理通知中的参数

通知里面获取被通知方法的参数并进行处理

在CDPlayer播放中，记录磁道的播放次数与播放本身是不同的关注点，因此不应该属于playTrack()方法，故统计次数是切面的任务，切面获取磁道名称并记录其出现的次数。

目标类 CDPlayer.java
```java
@Component
public class CDPlayer {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    @Autowired
    private CompactDisc cd;

    public void playTrack() {
        System.out.println(cd);
    }

    public void play(int index) {
        System.out.println(cd.getTracks().get(index-1) + " is playing....");
    }
}
```

CompactDisc.java
```java
@Component
public class CompactDisc {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    private String title;
    private List<String> tracks;

    public CompactDisc(){} // 如果自定义了构造方法，必须显式地定义默认构造方法，否则 Spring 无法实现自动注入

    public CompactDisc(String title, List<String> tracks) {
        this.title = title;
        this.tracks = tracks;
    }

    public List<String> getTracks() {
        return tracks;
    }
}
```
切面类 TrackCounter.java
```java
@Aspect
@Component
public class TrackCounter {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    // execution(...play(int))的 int 是被通知的方法的获得的参数的类型
    // 通过 && args(trackNumber) 表示被通知方法的实参也将传递给通知方法
    @Pointcut("execution(* com.zexing.aspectj.trackPlayCount.CDPlayer.play(int)) && args(trackNumber)")
    public void pointcut(int trackNumber) { // 形参名必须和 args()一致
    }

    // @Around("trackPlayed(trackNumber)")中的 "trackNumber"
    // 不必与 args() 相同 ，可以另外命名的，但必须保证本通知内一致即可。
    @Around("trackPlayed(trackNumber)")
    public void countTrack(ProceedingJoinPoint pjp, int trackNumber) {
        try {
            pjp.proceed(); //调用被通知方法
            // 每次调用被通知方法成功之后，音轨的播放次数+1
            int currentCount = getTrackCurrentCount(trackNumber);
            map.put(trackNumber, ++currentCount);
        } catch (Throwable e) {
            // 调用出现异常后的代码
            System.out.println("CDPlayer 播放异常！");
        }
    }

    public int getTrackCurrentCount(int trackNumber) {
        return map.containsKey(trackNumber) ? map.get(trackNumber) : 0;
    }
}
```
这里我们用 @PointCut 注解了一个带有int参数的方法 trackPlayed() ，将其定义为一个切点。在 @PointCut 中我们去匹配 CompactDisc 类中的 play() 方法，并且定制了其参数类型为int，这里当这样限制了被切入方法的类型，就必须同时使用 args() 标识符来指明变量的名字。

配置类 CompactDiscConfig.java 

```java
@Configuration
@EnableAspectJAutoProxy     //启用自动代理
@ComponentScan
public class CompactDiscConfig {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    /*
    由于CompactDisc类中有两个构造方法，Spring在匹配 bean 时出现冲突，所以必须显式指定一个bean。
    否则将出现异常，大概就是说，我只一个 bean 就够了，但给我两个，叫我怎么选啊：
    No qualifying bean of type 'com.san.spring.aop.CD' available:
    expected single matching bean but found 2: CD,setCD
    */
    @Bean
    @Primary //首选bean
    public CompactDisc compactDisc(){
        String title = "唐朝";
        List<String> tracks = new ArrayList<String>();
        tracks.add("梦回唐朝");
        tracks.add("太阳");
        tracks.add("九拍");
        tracks.add("天堂");
        tracks.add("选择");
        tracks.add("飞翔鸟");
        tracks.add("世纪末之梦");
        tracks.add("月梦");
        tracks.add("不要逃避");
        tracks.add("传说");

        return new CompactDisc(title, tracks);
    }
}
```
测试类 
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CompactDiscConfig.class)
public class CompactDiscConfigTest {

    @Autowired
    private CDPlayer player;

    @Autowired
    private TrackCounter trackCounter;

    @Test
    public void testTrackCounter() {

        // 执行 play()一次，该音轨的播放次数加1
        player.play(1);
        player.play(1);
        player.play(2);
        player.play(3);
        player.play(5);

        // 与期待的次数一致，则测试通过
        assertEquals(2, trackCounter.getTrackCurrentCount(1));
        assertEquals(1, trackCounter.getTrackCurrentCount(2));
        assertEquals(1, trackCounter.getTrackCurrentCount(3));
        assertEquals(0, trackCounter.getTrackCurrentCount(4));
        assertEquals(1, trackCounter.getTrackCurrentCount(5));
    }

}
```
### 通过注解引入新功能
上面我们都是在方法上新增功能方法，同样的，切面可以实现类级别上新增方法。
如图示，切面引入新方法流程

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190306224843558.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNDU2NTk3Nw==,size_16,color_FFFFFF,t_70)

当引入接口的方法被调用时，代理会把此调用委 托给实现了新接口的某个其他对象。实际上，一个bean的实现被拆分到了多个类中。

**场景描述**：

　　　　在一场音乐会中，添加潮剧表演的环节，但在原来的音乐会表演类（Concert.java）中没有这个方法，所以需要为这个表演类再新加一个方法。

1. 创建表演者接口
```java
public interface Performer {
    public void performAdded();
}
```
2. 指派一个表演者去表演潮剧
```java
@Component
public class OnePerformer implements Performer {

    /**
     *
     * Created by Joson on 2/26/2019.
     */
    public void performAdded() {
        System.out.println("潮剧表演...");
    }
}
```
3. 通过主持人（切面类）的介绍
```java
@Component
@Aspect
public class PerformIntroducer {
    /**
     *
     * Created by Joson on 2/26/2019.
     */
    //通过 PerformIntroducer 的介绍，潮剧表演者进入音乐会进行加场表演
    @DeclareParents(value = "com.zexing.aspectj.concert.Performance+",defaultImpl = OnePerformer.class )
    public static Performer performer;
}
```
 通过@DeclareParents注解，将Performer接口引入 到Performance bean中。
 
 @DeclareParents注解由三部分组成：

- value属性指定了哪种类型的bean要引入该接口。在本例中，也 就是所有实现Performance的类型。（标记符后面的加号表示 是Performance的所有子类型，而不是Performance本 身。）
- defaultImpl属性指定了为引入功能提供实现的类。在这里， 我们指定的是OnePerformer提供实现。
- @DeclareParents注解所标注的静态属性指明了要引入的接 口。在这里，我们所引入的是Performer接口。

4. 开始音乐会
```java
    @Test
    public void addPerform(){
        assertNotNull(concert);
        concert.perform();
        System.out.println("--下面进行临时加场表演--");
        Performer p = (Performer) concert;
        p.performAdded();
    }
```
5. 音乐会
```java
手机静音
得到座位
silencing cell phones
Taking seats
Performing...
鼓掌!!!
CLAP CLAP CLAP！
--下面进行临时加场表演--
潮剧表演...
```

在Spring中，注解和自动代理提供了一种便利的方式来创建切面，它非常简单，并且只设计最少的Spring配置，但是，面向注解的切面有一个明显的不足点：你必须能够为通知类添加注解，为了做到这一点，必须要有源码。

## 使用XML
 - xml中切面的声明标签

1. aop:advisor :定义AOP通知器
1. aop:after :定义AOP后置通知
1. aop:after-returning :定义AOP返回通知
1. aop:after-throwing :定义AOP异常通知
1. aop:around :定义AOP环绕通知
1. aop:aspect :定义一个切面
1. aop:aspectj-autoproxy :启用@AspectJ注解
1. aop:before :定义一个AOP前置通知
1. aop:poiontcut :定义一个切点

-  声明前后置通知
```xml
<!--声明切面 -->
    <aop:config>
        <aop:aspect ref="audience">       <!--引用audience Bean-->

            <aop:before pointcut="execution(* com.zexing.aspectj.concert.Performance.perform(..))" method="silenceCellPhones"/>

            <aop:before pointcut="execution(* com.zexing.aspectj.concert.Performance.perform(..))" method="takeSeats"/>

            <aop:after-returning pointcut="execution(* com.zexing.aspectj.concert.Performance.perform(..))" method="applause"/>

            <aop:after-throwing pointcut="execution(* com.zexing.aspectj.concert.Performance.perform(..))" method="demandRefun"/>

        </aop:aspect>
    </aop:config>
```
- 声明切点
```xml
<!--声明切点 -->
    <aop:config>
        <aop:aspect ref="audience">
            <aop:pointcut id="performance" expression="execution(* com.zexing.aspectj.concert.Performance.perform(..))" />

            <aop:before pointcut-ref="performance" method="silenceCellPhones"/>

            <aop:before pointcut-ref="performance" method="takeSeats"/>

            <aop:after-returning pointcut-ref="performance" method="applause"/>

            <aop:after-throwing pointcut-ref="performance" method="demandRefun"/>

        </aop:aspect>
    </aop:config>
```
- 声明环绕通知
```xml
<!-- 声明环绕通知-->
    <aop:config>
        <aop:aspect ref="audience">       <!--引用audience Bean-->
            <aop:pointcut id="performance" expression="execution(* com.zexing.aspectj.concert.Performance.perform(..))"  />

            <aop:around pointcut-ref="performance" method="watchPerformance"/>

        </aop:aspect>
    </aop:config>
```

- 为通知传递参数
```xml
<!-- 为通知传递参数-->
    <bean id="player" class="com.zexing.aspectj.trackPlayCount.CDPlayer" />
    <!-- 构造器注入属性-->
    <bean id="cd" class="com.zexing.aspectj.trackPlayCount.CompactDisc" >
        <constructor-arg name="title" value="唐朝" />
        <constructor-arg name="tracks">
            <list>
                <value>梦回唐朝</value>
                <value>太阳</value>
                <value>九拍</value>
                ...
            </list>
        </constructor-arg>

    </bean>
    <bean id="trackCounter" class="com.zexing.aspectj.trackPlayCount.TrackCounter" />

    <aop:config>
        <aop:aspect ref="trackCounter">
            <aop:pointcut id="trackPlayed" expression="execution(* com.zexing.aspectj.trackPlayCount.CDPlayer.play(int)) and args(trackNumber)" />
            <!-- 这里args()的trackNumber 必须与类中countTrack()方法的参数名保持一致-->
            <aop:around pointcut-ref="trackPlayed" method="countTrack" />
        </aop:aspect>
    </aop:config>
```
- 为通知新增方法
```xml
<!-- 为bean 新增方法-->
    <bean id="onePerformer" class="com.zexing.aspectj.concert2.OnePerformer" />

    <aop:config>
        <aop:aspect ref="audience">       <!--引用audience Bean-->
            <aop:declare-parents types-matching="com.zexing.aspectj.concert.Performance"
                                 implement-interface="com.zexing.aspectj.concert2.Performer"
                                 delegate-ref="onePerformer" />
        </aop:aspect>
    </aop:config>
```
