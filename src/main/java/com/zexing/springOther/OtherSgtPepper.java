package com.zexing.springOther;

import com.zexing.spring.interfact.CompactDisc;
import org.springframework.stereotype.Component;

@Component
public class OtherSgtPepper implements CompactDisc {
    public void play() {
        System.out.println("otherSgtPepper");
    }
}
