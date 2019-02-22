package com.zexing.propertySource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/zexing/propertySource/app.properties")
public class PersonCofig {

    @Autowired
    Environment environment;

    @Bean(name = "Jack")
    public Student student1(){
        return new Student(environment.getProperty("stu.name"),
                environment.getProperty("stu.sex"));
    }

    @Bean(name = "Mike")
    public Student student(){
        return new Student("Mike","male");
    }

}
