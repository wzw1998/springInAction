package com.zexing.aspectj.concert2;

import org.springframework.stereotype.Component;

@Component
public class OnePerformer implements Performer {

    /**
     *
     * Created by Joson on 2/26/2019.
     */
    public void performAdded() {
        System.out.println("潮剧表演...");
    }
}
