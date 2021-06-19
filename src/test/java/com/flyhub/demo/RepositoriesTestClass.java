package com.flyhub.demo;

import com.flyhub.demo.entities.AppRole;
import com.flyhub.demo.entities.AppUser;
import com.flyhub.demo.repositories.AppRoleRepository;
import com.flyhub.demo.repositories.AppUserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class RepositoriesTestClass {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    void testAddingUser() {

        AppUser appUser = appUserRepository.save(new AppUser("admin", "pass"));

        Assert.notNull(appUser, "found the new appUser object null");

        Assert.isTrue(appUserRepository.count() > 1, "number of users added does not match!");

    }

    @Test
    @Order(2)
    void testRemovingUser() {

        appUserRepository.deleteAll();

        Assert.isTrue(appUserRepository.count() == 0, "number of users does not match!");
    }

    @Test
    @Order(3)
    void testAddingRole() {
        AppRole role = appRoleRepository.save(new AppRole("ADMIN", true));

        Assert.notNull(role, "found the new role object null");

        Assert.isTrue(appRoleRepository.count() == 1, "number of roles added does not match after adding!");
    }

    @Test
    @Order(4)
    void testRemovingrole() {
        appRoleRepository.deleteAll();

        Assert.isTrue(appRoleRepository.count() == 0, "number of roles does not match after deleteing!");
    }

    @Test
    @Order(5)
    void testAssigningRole() {
        //WHEN
        String username = "user";

        //create new user
        AppUser appUser = appUserRepository.save(new AppUser(username, "pass"));

        Assert.notNull(appUser, "found the new appUser object null");

        Assert.isTrue(appUserRepository.count() == 1, "number of users added does not match!");

        //create a couple of roles in the DB
        Set<AppRole> system_roles = Stream.of(
                new AppRole("SUPER_USER", true),
                new AppRole("ADMIN", true),
                new AppRole("USER", true),
                new AppRole("EDITOR", true))
                .collect(Collectors.toSet());
        //save
        system_roles.forEach(appRoleRepository::save);

        Assert.isTrue(appRoleRepository.count() == 4, "number of roles does not match after deleteing!");

        //assign a role to the user
        AppUser user = appUserRepository.findByUserName(username)
                .orElse(null);

        Assert.notNull(user, String.format("user with username %s not found", username));

        //Assign the role of A SUPER_USER
        AppRole role = appRoleRepository.findRoleByName("SUPER_USER")
                .orElse(null);

        Set<AppRole> user_roles = user.getRoles();
        user_roles.add(role);

        appUserRepository.save(user);

    }
}
