package com.zexing.spring.java_bean;

import com.zexing.spring.auto_bean.CDPlayer;
import com.zexing.spring.auto_bean.SgtPeppers;
import com.zexing.spring.interfact.CompactDisc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CDPlayerConfig1 {

    @Bean
    public CompactDisc sgtPeppers(){
        return new SgtPeppers();
    }

//    //随机选择CD播放
//    @Bean
//    public CompactDisc randomBeatLesCD() {
//        int choice = (int) Math.floor(Math.random() * 2);
//        if (choice == 0) {
//            return new SgtPeppers();
//        } else {
//            return new OtherSgtPepper();
//        }
//    }

//    @Bean
//    public CDPlayer cdPlayer(){
//       return new CDPlayer(sgtPeppers());
//    }

    @Bean
    public CDPlayer cdPlayer(CompactDisc compactDisc){
        return new CDPlayer(compactDisc);
    }

//    @Bean
//    public CDPlayer otherCDPlayer(){
//        return new CDPlayer(sgtPeppers());
//    }
}
