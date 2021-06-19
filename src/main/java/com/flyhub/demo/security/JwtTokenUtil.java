package com.flyhub.demo.security;

import com.flyhub.demo.authentication.AppUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 *
 * @author Benjamin E Ndugga
 */
@Log4j2
@Component
public class JwtTokenUtil {

    private final String jwtSecret = "thisshouldbealongsecretekeythatwillgenerateanicelongjwtkeyforallusersforthissaccoxapplication";
    private final String jwtIssuer = "flyhub.ug.com";

    public String generateAccessToken(AppUserDetails appUserDetails) {

        return Jwts.builder()
                .setSubject(appUserDetails.getUsername())
                .setIssuer(jwtIssuer)
                .claim("id", appUserDetails.getUserId())
                .claim("username", appUserDetails.getUsername())
                .claim("authorities", appUserDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", String.class);
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

   

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getCause());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getCause());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getCause());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getCause());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getCause());
        }
        return false;
    }

}
