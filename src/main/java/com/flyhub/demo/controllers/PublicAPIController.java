package com.flyhub.demo.controllers;

import com.flyhub.demo.authentication.AppUserDetails;
import com.flyhub.demo.models.AuthRequest;
import com.flyhub.demo.models.OperationResult;
import com.flyhub.demo.security.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Benjamin E Ndugga
 */
@Log4j2
@Controller
@RequestMapping("/api/v1/public")
public class PublicAPIController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(path = "/login", consumes = {"text/plain", "application/json"}, produces = {"application/json"})
    public ResponseEntity<Object> login(@RequestBody AuthRequest request) {
        try {

            log.info("request for JWT token.");

            log.info("authenticate user credentials...");

            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            log.info("username and password authenticated...");

            Object principal = authenticate.getPrincipal();

            if (principal.getClass().isAssignableFrom(AppUserDetails.class)) {

                AppUserDetails appUserDetails = (AppUserDetails) authenticate.getPrincipal();

                log.info(String.format("principle object is a %s ", AppUserDetails.class.getName()));

                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(appUserDetails))
                        .body(appUserDetails);

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new OperationResult(401, "Access to this resource is not permitted!"));
            }
        } catch (BadCredentialsException | LockedException | DisabledException | CredentialsExpiredException ex) {

            log.info(String.format("Authentication failed: %s", ex.getLocalizedMessage()));

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new OperationResult(401, "Authentication failed", ex.getLocalizedMessage()));
        }
    }
}