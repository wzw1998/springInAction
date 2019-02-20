package com.zexing.spring.java_bean;

import com.zexing.spring.interfact.CompactDisc;
import com.zexing.spring.interfact.MediaPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig1.class)
public class BeanTest {
    @Autowired
    MediaPlayer mediaPlayer;

    @Autowired
    CompactDisc compactDisc;

    @Test
    public void t1(){
        System.out.println(mediaPlayer.getClass());
        System.out.println(compactDisc.getClass());
        assertNotNull(mediaPlayer);
        assertNotNull(compactDisc);
    }
}
