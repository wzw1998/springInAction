package com.zexing.spring.auto_bean;

import com.zexing.spring.interfact.CompactDisc;
import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc {

    public void play() {
        System.out.println("SgtPeppers");
    }
}
