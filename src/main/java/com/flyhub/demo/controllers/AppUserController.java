package com.flyhub.demo.controllers;

import com.flyhub.demo.models.OperationResult;
import com.flyhub.demo.services.AppUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Benjamin E Ndugga
 */
@Controller
@RequestMapping("/api/v1/appuser")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @ResponseBody
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<OperationResult> listAllUsers() {
        return new ResponseEntity<>(appUserService.listAllUsers(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(path = "/{username}", produces = {"application/json"})
    public ResponseEntity<OperationResult> listUserByUsername(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(appUserService.listUserByUsername(username), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(path = "/addroles/{username}", produces = {"application/json"})
    public ResponseEntity<OperationResult> assignUserToRole(@PathVariable(name = "username") String username, @RequestBody List<String> rolenames) {
        return new ResponseEntity<>(appUserService.assignRoles(username, rolenames), HttpStatus.OK);
    }

}
