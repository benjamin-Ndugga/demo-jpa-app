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

@SpringBootApplication
public class DemoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAppApplication.class, args);
    }

    //load some dummy data
    @Bean
    public CommandLineRunner commandLineRunner(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository) {
        return (String[] args) -> {

            Set<AppRole> app_roles = Stream.of(
                    new AppRole("ADMIN", true),
                    new AppRole("SUPER_ADMIN", true),
                    new AppRole("EDITOR", true),
                    new AppRole("FINANCE", true),
                    new AppRole("ACCOUNTING", true)
            ).collect(Collectors.toSet());

            app_roles.forEach(appRoleRepository::save);

            Set<AppUser> app_users = Stream.of(
                    new AppUser("admin", "pass"),
                    new AppUser("user1", "pass"),
                    new AppUser("user2", "pass"))
                    .collect(Collectors.toSet());

            app_users.forEach(appUserRepository::save);
        };
    }
}
