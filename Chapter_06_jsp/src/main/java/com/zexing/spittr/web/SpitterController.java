package com.zexing.spittr.web;

import com.zexing.spittr.bean.Spitter;
import com.zexing.spittr.repository.SpitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
    /**
     *
     * Created by Joson on 2/28/2019.
     */
    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(SpitterRepository spitterRepository){
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String showRegistrationForm(Model model){
        model.addAttribute(new Spitter());//对应registerForm.jsp中 <sf:form> 的commandName属性值
        return "registerForm";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String processRegistration(@Valid Spitter spitter,Errors errors){
        if (errors.hasErrors()) {
            System.out.println("222");
            return "registerForm";
        }
        spitterRepository.save(spitter);
        return "redirect:/spitter/" + spitter.getUsername();
    }


    @RequestMapping(value = "/profile/{userName}")
    public String showSpitterProfile(@PathVariable String userName, Model model){   //这里展现user的信息，需要模型数据传递给视图
        model.addAttribute(spitterRepository.findSpitterByUserName(userName));
        return "profile";
    }

}
