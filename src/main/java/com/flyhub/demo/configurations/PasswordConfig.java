package com.flyhub.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Benjamin E Ndugga
 */
@Configuration
public class PasswordConfig {

    /**
     *
     * @return the password encryption algorithm. All user passwords are encoded
     * and decoded with this.
     */
    @Bean
    public PasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder(10);
    }

}
