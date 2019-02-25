package com.zexing.aspectj.trackPlayCount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    @Autowired
    private CompactDisc cd;

    public void playTrack() {
        System.out.println(cd);
    }

    public void play(int index) {
        System.out.println(cd.getTracks().get(index-1) + " is playing....");
    }
}
