package com.zexing.spittr.config;

import com.zexing.spittr.web.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;


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

  @Override
  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
    registration.setMultipartConfig(          //启用对multipart请求的支持
            new MultipartConfigElement("/tmp/spittr/uploads")
    );
  }

}