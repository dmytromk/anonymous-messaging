package org.anonmes.messenger.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

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
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Map<String, Object> claims, String subject) {

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("AnonMessenger")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .signWith(key)
                .compact();
    }
}
