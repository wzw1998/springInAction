package com.zexing.aspectj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
//@ContextConfiguration(locations = "classpath*:app.xml")
public class ConcertConfigTest {

//    @Autowired
//    private Audience audience;

    @Autowired
    private Performance concert;

    @Test
    public void concertStart(){

//        assertNotNull(audience);
        assertNotNull(concert);
        concert.perform();
    }


}