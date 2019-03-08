package com.zexing.spittr.web;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc //启用Spring MVC
@ComponentScan("com.zexing.spittr.web") //启用组件扫描
public class WebConfig extends WebMvcConfigurerAdapter {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // TODO Auto-generated method stub
    super.addResourceHandlers(registry);
  }


  // Tiles
  @Bean
  public TilesConfigurer tilesConfigurer() {
    TilesConfigurer tiles = new TilesConfigurer();
    tiles.setDefinitions(new String[] {
            "/WEB-INF/layout/tiles.xml",      //指定Tiles定义的位置
            "/WEB-INF/views/**/tiles.xml"     //遍历“WEB-INF/”的所有子目录来查找Tile定义。
    });
    tiles.setCheckRefresh(true);    //启用刷新功能
    return tiles;
  }

  @Bean
  public ViewResolver viewResolver() {
    return new TilesViewResolver();
  }

  /**
   * 配置静态资源的处理
   * 此配置要求DispatcherServlet将对静态资源的请求转发到Servlet容器中默认的Servlet上，
   * 而非使用DispatcherServlet本身来处理此类请求
   */
  public void configureDefaultServletHandling(
          DefaultServletHandlerConfigurer configurer) {
    // TODO Auto-generated method stub
    //super.configureDefaultServletHandling(configurer);
    configurer.enable();
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("/resources/messages");
    messageSource.setCacheSeconds(10);
    return messageSource;

  }
}
