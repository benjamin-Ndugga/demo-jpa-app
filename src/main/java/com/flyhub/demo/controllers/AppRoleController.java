package com.flyhub.demo.controllers;

import com.flyhub.demo.services.AppRoleService;
import com.flyhub.demo.models.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Benjamin E Ndugga
 */
@Controller
@RequestMapping("/api/v1/approle")
public class AppRoleController {

    @Autowired
    private AppRoleService appRoleService;

    @ResponseBody
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<OperationResult> listAllRoles() {
        return new ResponseEntity<>(appRoleService.listAllRoles(), HttpStatus.OK);
    }

}
