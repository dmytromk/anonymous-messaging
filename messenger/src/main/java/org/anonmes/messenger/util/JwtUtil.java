package org.anonmes.messenger.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${app.secret}")
    private String secret;

    public boolean validateToken(String token, String username) {
        String usernameInToken = getUsername(token);
        return (usernameInToken.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpDate(token);
        return expiration.before(new Date());
    }

    public String generateToken(String  username) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, username);
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpDate(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("ZELENSKYI")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
