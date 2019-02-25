package com.zexing.aspectj.concert;

import org.springframework.stereotype.Component;

@Component
public class Concert implements Performance {

    public void perform() {
        System.out.println("Performing...");
    }

}
