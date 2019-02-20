package com.zexing.spring.xml_bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @RunWith就是一个运行器
 *@RunWith(JUnit4.class)就是指用JUnit4来运行
 *@RunWith(SpringJUnit4ClassRunner.class),让测试运行于Spring测试环境
 */
@RunWith(JUnit4.class)
//@RunWith(SpringJUnit4ClassRunner.class)
// 这个注解会在测试开始的时候自动创建Spring的应用上下文，在用了这个注解的情况下，会与ClassPathXmlApplicationContext有冲突，得用@ContextConfiguration加载xml文件，
//参考链接：https://blog.csdn.net/weixin_40672761/article/details/83859084
//@ContextConfiguration(locations="classpath:/constructor-context.xml")
public class HDPlayerTest {

//    @Resource
//    private  HDPlayer hdPlayer;

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("constructor-context.xml");
    HDPlayer hdPlayer = (HDPlayer) applicationContext.getBean("hdPlayer");

    @Test
    public void play() {
        assertNotNull(hdPlayer);
    }
}