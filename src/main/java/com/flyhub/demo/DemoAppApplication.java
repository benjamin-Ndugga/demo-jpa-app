package com.flyhub.demo;

import com.flyhub.demo.entities.AppRole;
import com.flyhub.demo.entities.AppUser;
import com.flyhub.demo.repositories.AppRoleRepository;
import com.flyhub.demo.repositories.AppUserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAppApplication.class, args);
    }

    //load some dummy data
    @Bean
    public CommandLineRunner commandLineRunner(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        return (String[] args) -> {

            Set<AppRole> app_roles = Stream.of(
                    new AppRole("ADMIN", true),
                    new AppRole("SUPER_ADMIN", true),
                    new AppRole("USER", true)
            ).collect(Collectors.toSet());

            app_roles.forEach(appRoleRepository::save);

            Set<AppUser> app_users = Stream.of(
                    new AppUser("admin", passwordEncoder.encode("pass"), true, true, true, true),
                    new AppUser("user1", passwordEncoder.encode("pass"), true, true, true, true),
                    new AppUser("user2", passwordEncoder.encode("pass"), true, true, true, true))
                    .collect(Collectors.toSet());

            app_users.forEach(appUserRepository::save);
            
            //assign user 'admin' to the 'SUPER_ADMIN' role
            AppUser appUser = appUserRepository.findByUserName("admin").orElse(null);
            AppRole appRole = appRoleRepository.findRoleByName("SUPER_ADMIN").orElse(null);
            
            Set<AppRole> roles = appUser.getRoles();
            roles.add(appRole);
            
            appUserRepository.save(appUser);
        };
    }
}
