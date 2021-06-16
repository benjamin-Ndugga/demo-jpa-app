package com.flyhub.demo.services;

import com.flyhub.demo.entities.AppRole;
import com.flyhub.demo.entities.AppUser;
import com.flyhub.demo.models.OperationResult;
import com.flyhub.demo.repositories.AppRoleRepository;
import com.flyhub.demo.repositories.AppUserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin E Ndugga
 */
@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    private static final String SUCCESS_MESSAGE = "Operation Succesfully Processed.";

    public OperationResult listAllUsers() {
        return new OperationResult(0, SUCCESS_MESSAGE, appUserRepository.findAll());
    }

    public OperationResult listUserByUsername(String username) {
        return new OperationResult(0, SUCCESS_MESSAGE,
                appUserRepository.findByUserName(username)
                        .orElse(null));
    }

    public OperationResult assignRoles(String username, List<String> roleNames) {

        AppUser appUser = appUserRepository.findByUserName(username)
                .orElse(null);

        List<AppRole> roles = appRoleRepository.findRoleByNames(roleNames);

        Set<AppRole> current_user_roles = appUser.getRoles();

        roles.forEach(role -> {
            if (!current_user_roles.contains(role)) {
                current_user_roles.add(role);
            }
        });

        System.out.println(appUser);

        return new OperationResult(0, SUCCESS_MESSAGE, appUserRepository.save(appUser));
    }
}
