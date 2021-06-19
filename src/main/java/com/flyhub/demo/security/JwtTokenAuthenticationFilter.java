package com.flyhub.demo.security;

import com.flyhub.demo.authentication.AppUserDetails;
import com.flyhub.demo.authentication.AppUserDetailsService;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Benjamin E Ndugga
 */
@Log4j2
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public JwtTokenAuthenticationFilter(JwtTokenUtil jwtTokenUtil, AppUserDetailsService appUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isNullOrEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            log.info("Authorization Header missing");
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();

        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }
        
        //this section can be optimized and avoid making another DB call
        
        AppUserDetails appUserDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));

        Collection<? extends GrantedAuthority> authorities = appUserDetails.getAuthorities();

        log.info(String.format("setting authorities: %s", authorities));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtTokenUtil.getUsername(token), null, authorities);

        log.info(String.format("setting authentication: %s ", authentication));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        chain.doFilter(request, response);
    }

}
