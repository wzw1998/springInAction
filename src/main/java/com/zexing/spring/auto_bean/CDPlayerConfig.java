package com.zexing.spring.auto_bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {CDPlayer.class})
public class CDPlayerConfig {

}
