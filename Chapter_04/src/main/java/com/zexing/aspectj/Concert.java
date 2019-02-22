package com.zexing.aspectj;

import org.springframework.stereotype.Component;

@Component
public class Concert implements Performance {

    public void perform() {
        System.out.println("Performing...");
    }

}
