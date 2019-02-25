package com.zexing.aspectj.trackPlayCount;

import org.springframework.context.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAspectJAutoProxy     //启用自动代理
@ComponentScan
public class CompactDiscConfig {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    /*
    由于CompactDisc类中有两个构造方法，Spring在匹配 bean 时出现冲突，所以必须显式指定一个bean。
    否则将出现异常，大概就是说，我只一个 bean 就够了，但给我两个，叫我怎么选啊：
    No qualifying bean of type 'com.san.spring.aop.CD' available:
    expected single matching bean but found 2: CD,setCD
    */
    @Bean
    @Primary //首选bean
    public CompactDisc compactDisc(){
        String title = "唐朝";
        List<String> tracks = new ArrayList<String>();
        tracks.add("梦回唐朝");
        tracks.add("太阳");
        tracks.add("九拍");
        tracks.add("天堂");
        tracks.add("选择");
        tracks.add("飞翔鸟");
        tracks.add("世纪末之梦");
        tracks.add("月梦");
        tracks.add("不要逃避");
        tracks.add("传说");

        return new CompactDisc(title, tracks);
    }
}
