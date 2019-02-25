package com.zexing.aspectj.trackPlayerCount;

import com.zexing.aspectj.trackPlayCount.CDPlayer;
import com.zexing.aspectj.trackPlayCount.CompactDisc;
import com.zexing.aspectj.trackPlayCount.CompactDiscConfig;
import com.zexing.aspectj.trackPlayCount.TrackCounter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CompactDiscConfig.class)
public class CompactDiscConfigTest {

    @Autowired
    private CDPlayer player;

    @Autowired
    private TrackCounter trackCounter;

    @Test
    public void testTrackCounter() {

        // 执行 play()一次，该音轨的播放次数加1
        player.play(1);
        player.play(1);
        player.play(2);
        player.play(3);
        player.play(5);

        // 与期待的次数一致，则测试通过
        assertEquals(2, trackCounter.getTrackCurrentCount(1));
        assertEquals(1, trackCounter.getTrackCurrentCount(2));
        assertEquals(1, trackCounter.getTrackCurrentCount(3));
        assertEquals(0, trackCounter.getTrackCurrentCount(4));
        assertEquals(1, trackCounter.getTrackCurrentCount(5));
    }

}