package com.zexing.aspectj.trackPlayCount;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompactDisc {
    /**
     *
     * Created by Joson on 2/25/2019.
     */
    private String title;
    private List<String> tracks;

    public CompactDisc(){} // 如果自定义了构造方法，必须显式地定义默认构造方法，否则 Spring 无法实现自动注入

    public CompactDisc(String title, List<String> tracks) {
        this.title = title;
        this.tracks = tracks;
    }

    public List<String> getTracks() {
        return tracks;
    }
}
