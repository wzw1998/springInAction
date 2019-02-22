package com.zexing.qualifier;

import com.zexing.qualifier.animal.Animal;
import com.zexing.qualifier.animal.annotation.Black;
import com.zexing.qualifier.animal.annotation.Cute;
import com.zexing.qualifier.animal.annotation.Red;
import com.zexing.qualifier.animal.impl.Cat;
import com.zexing.qualifier.animal.impl.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnimalConfig.class)
public class AnimalConfigTest {



        @Autowired
        @Cute
        @Red
        private Animal cat;

        @Autowired
        @Cute
        @Black
        private Animal dog;

        @Test
        public void myFavoriteIsCat(){
            assertTrue(cat instanceof Cat);
        }

        @Test
        public void myFavoriteIsGog(){assertTrue(dog instanceof Dog);}


}
