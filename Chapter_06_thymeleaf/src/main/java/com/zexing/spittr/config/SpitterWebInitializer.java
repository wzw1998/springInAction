package com.zexing.spittr.config;

import com.zexing.spittr.web.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { RootConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {  //指定配置类

    return new Class<?>[] { WebConfig.class };
  }

  @Override
  protected String[] getServletMappings() { //将DispatcherServlet映射到"/"

    return new String[] { "/" };
  }

}