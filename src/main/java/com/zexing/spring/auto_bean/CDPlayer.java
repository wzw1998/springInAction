package com.zexing.spring.auto_bean;

import com.zexing.spring.interfact.CompactDisc;
import com.zexing.spring.interfact.MediaPlayer;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MediaPlayer {

    private CompactDisc compactDisc;

    public CDPlayer(CompactDisc compactDisc){
        this.compactDisc = compactDisc;
    }

    public void play() {
        compactDisc.play() ;
    }
}
