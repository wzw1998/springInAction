package com.zexing.spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller//声明一个控制器
@RequestMapping({"/","/homepage"})
public class HomeController {

  @RequestMapping(method = GET)//处理get请求
  public String home(Model model) {
    return "home";
  }

}
