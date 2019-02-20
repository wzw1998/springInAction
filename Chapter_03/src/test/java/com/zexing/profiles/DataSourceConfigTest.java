package com.zexing.profiles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class DataSourceConfigTest {

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes=DataSourceConfig.class)
    @ActiveProfiles("prod")//激活profile
    public static class ProductionDataSourceTest {
        @Autowired
        private DataSource dataSource;

        @Test
        public void shouldBeEmbeddedDatasource() {
            assertNotNull(dataSource);
        }
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration("classpath:datasource-config.xml")
    @ActiveProfiles("development")
    public static class DevelopmentDataSourceTest_XMLConfig {
        @Autowired
        private DataSource dataSource;

        @Test
        public void shouldBeEmbeddedDatasource() {
            assertNotNull(dataSource);
        }
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration("classpath:datasource-config.xml")
    @ActiveProfiles("production")
    public static class ProductionDataSourceTest_XMLConfig {
        @Autowired
        private DataSource dataSource;

        @Test
        public void shouldBeEmbeddedDatasource() {
            assertNotNull(dataSource);
        }
    }

}