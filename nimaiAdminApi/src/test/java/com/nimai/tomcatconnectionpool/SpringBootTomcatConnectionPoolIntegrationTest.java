//package com.nimai.tomcatconnectionpool;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import javax.sql.DataSource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.nimai.admin.NimaiAdminApplication;
//
//
//
//@RunWith(SpringRunner.class)
//
//@SpringBootTest(classes = {NimaiAdminApplication.class})
//
//@TestPropertySource(properties = "spring.datasource.type=org.apache.tomcat.jdbc.pool.DataSource")
//
//public class SpringBootTomcatConnectionPoolIntegrationTest {
//
//    
//
//    @Autowired
//
//    private DataSource dataSource;
//
//    
//
//    @Test
//
//    public void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
//
//        assertThat(dataSource.getClass().getName()).isEqualTo("org.apache.tomcat.jdbc.pool.DataSource");
//
//    }
//
//}