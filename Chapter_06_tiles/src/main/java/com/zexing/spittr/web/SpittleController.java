package com.zexing.spittr.web;

import com.zexing.spittr.bean.Spittle;
import com.zexing.spittr.repository.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/spittles")
public class SpittleController {
    /**
     *
     * Created by Joson on 2/27/2019.
     */
    private SpittleRepository spittleRepository;
    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    @Autowired
    public SpittleController(SpittleRepository spittleRepository){

        this.spittleRepository = spittleRepository;
    }

    /*
    第一种方案： 使用 Model
    @RequestMapping(method = RequestMethod.GET)
    public String splittles(Model model){
        model.addAttribute(spittleRepository.findSpittles(Long.MAX_VALUE,20));  //将返回的spittle list添加到模型中去。
        return "spittles";  //返回spittles作为视图的名字，这个视图会渲染模型。
    }
    Model 实质上是一个Map集合（即key-value），当不指定key值，那么key会
    根据值的对象类型推断确定，如下面例子的键将会推断为spittleList。

    第二种方案：用 Map 代替 Model

    public String splittles(Map model){
        model.put("spittleList",spittleRepository.findSpittles(Long.MAX_VALUE,20));
        return "spittles";
    }
    第三种方案：
    当处理器方法像这样返回对象或集合时，这个值会放到模型中，模型的key会根据其
    类型推断得出（在本例中，也就是spittleList）。
    而逻辑视图的名称将会根据请求路径推断得出。因为这个方法处理针
    对“/spittles”的GET请求，因此视图的名称将会是spittles（去掉开头的斜线）。

    @RequestMapping(method = RequestMethod.GET)
    public List<Spittle> splittles(){
        return spittleRepository.findSpittles(Long.MAX_VALUE,20);
    }
*/
    @RequestMapping(method = RequestMethod.GET)
    public List<Spittle> spittles(  //当参数不存在使用默认值
            @RequestParam(value = "max",defaultValue = MAX_LONG_AS_STRING) long max,
            @RequestParam(value = "count",defaultValue = "20") int count) {

        return spittleRepository.findSpittles(max,count);

    }

    @RequestMapping(value = "/{spittleId}",method = RequestMethod.GET)
    public String spittle (
            @PathVariable long spittleId,Model model){
        model.addAttribute(spittleRepository.findOneSpittle(spittleId)) ;
        return "spittle";
    }

}
