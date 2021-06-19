package com.flyhub.demo.configurations;

import com.flyhub.demo.authentication.AppUserDetailsService;
import com.flyhub.demo.security.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *
 * @author Benjamin E Ndugga
 */
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;
    private final AppUserDetailsService appUserDetailsService;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder encoder, AppUserDetailsService appUserDetailsService, JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
        this.encoder = encoder;
        this.appUserDetailsService = appUserDetailsService;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //enbale CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        //set the session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        //set permissions
        http = http.authorizeRequests()
                //open and public endpoints
                .antMatchers("/api/v1/public/**").permitAll()
                .antMatchers("/css/*", "/js/*", "/login", "/logout", "/actuator/**").permitAll()
                .antMatchers("/swagger**", "/swagger-resources/**", "/v2/api**", "/webjars/**").permitAll()
                //closed and private endpoints
                .antMatchers(HttpMethod.GET, "/api/v1/appuser**").hasAnyAuthority("SUPER_ADMIN", "ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/appuser/addroles").hasAnyRole("SUPER_ADMIN", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/approle/**").hasAnyRole("SUPER_ADMIN")
                .anyRequest().authenticated()
                .and();

        // JwtTokenFilter before the Spring Security internal UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * we need access to the authentication manager, by default, it’s not
     * publicly accessible, and we need to explicitly expose it as a bean
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
