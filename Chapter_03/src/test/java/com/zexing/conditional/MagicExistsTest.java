package com.zexing.conditional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MagicConfig.class)
public class MagicExistsTest {

  @Autowired
  private ApplicationContext context;
  
  /*
   * This test will fail until you set a "magic" property.
   * You can set this property as an environment variable, a JVM system property, by adding a @BeforeClass
   * method and calling System.setProperty() or one of several other options.
   */
  @BeforeClass
  public static void setProperty(){

    System.setProperty("magic","value");

  }

  @Test
  public void shouldNotBeNull() {
    assertTrue(context.containsBean("magicBean"));
  }
  
}
