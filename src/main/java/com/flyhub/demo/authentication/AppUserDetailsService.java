package com.flyhub.demo.authentication;

import com.flyhub.demo.entities.AppUser;
import com.flyhub.demo.repositories.AppUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin E Ndugga
 */
@Log4j2
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(String.format("checking username: %s", username));

        AppUser appUser = appUserRepository.findByUserName(username).orElse(null);

        log.info(String.format("found user info : %s", appUser));

        if (appUser == null) {

            log.error(String.format("username %s not found", username));

            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }

        return new AppUserDetails(appUser);
    }
}
