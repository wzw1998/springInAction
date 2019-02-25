package com.zexing.scope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NotepadConfig.class)
public class NotepadConfigTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void prototypeScopeWithDifferBean(){

        Notepad  differ1 = context.getBean(Notepad.class);
        Notepad differ2 = context.getBean(Notepad.class);

        assertNotSame( differ1,differ2);
    }


    @Test
    public void singleScopeWithSameBean(){

        UniqueThing same1 = context.getBean(UniqueThing.class);
        UniqueThing same2 = context.getBean(UniqueThing.class);

        assertSame(same1,same2);
    }

}