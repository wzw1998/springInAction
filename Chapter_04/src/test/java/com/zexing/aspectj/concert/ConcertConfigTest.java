package com.zexing.aspectj.concert;

import com.zexing.aspectj.concert.ConcertConfig;
import com.zexing.aspectj.concert.Performance;
import com.zexing.aspectj.concert2.OnePerformer;
import com.zexing.aspectj.concert2.Performer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
//@ContextConfiguration(locations = "classpath*:app.xml")
public class ConcertConfigTest {

    @Autowired
    private Performance concert;

    @Test
    public void concertStart(){

        assertNotNull(concert);
        concert.perform();
    }

    @Test
    public void addPerform(){
        assertNotNull(concert);
        concert.perform();
        System.out.println("--下面进行临时加场表演--");
        Performer p = (Performer) concert;
        p.performAdded();
    }

}