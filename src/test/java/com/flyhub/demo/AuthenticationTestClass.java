package com.flyhub.demo;

import com.flyhub.demo.authentication.AppUserDetailsService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Benjamin E Ndugga
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class AuthenticationTestClass {

    @Autowired
    private AppUserDetailsService appUserDetailsService; 
    
    
    private void testLoadingUserPrincipal(){
      
    }
}
