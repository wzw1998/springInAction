package com.zexing.spring.xml_bean;

import com.zexing.spring.interfact.CompactDisc;

public class BlankDisc implements CompactDisc {
    private String title;
    private String artist;
    public BlankDisc(String title,String artist){
        this.title = title;
        this.artist = artist;
    }
    public void play() {
        System.out.println("Playing" + title + "by" + artist);
    }
}
