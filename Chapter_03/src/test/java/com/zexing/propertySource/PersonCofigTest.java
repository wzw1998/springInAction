package com.zexing.propertySource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersonCofig.class)
public class PersonCofigTest {

    @Autowired
    private Student Mike;

    @Autowired
    private Student Jack;


    @Test
    public void setStudentWithHardCode(){
        assertEquals("Mike",Mike.getName());
    }

    @Test
    public void setStuWithPropertySource(){assertEquals("jack",Jack.getName());}

    @Test
    public void setStuWithXml(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:/com/zexing/propertySource/app.xml");
        Student jack = (Student) applicationContext.getBean("Jack1");
        assertEquals("jack",jack.getName());
    }


}