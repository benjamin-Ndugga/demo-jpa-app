package com.flyhub.demo.services;

import com.flyhub.demo.models.OperationResult;
import com.flyhub.demo.repositories.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin E Ndugga
 */
@Service
public class AppRoleService {

    @Autowired
    private AppRoleRepository appRoleRepository;
    private static final String SUCCESS_MESSAGE = "Operation Succesfully Processed.";

    public OperationResult listAllRoles() {
        return new OperationResult(0, SUCCESS_MESSAGE, appRoleRepository.findAll());
    }
}
