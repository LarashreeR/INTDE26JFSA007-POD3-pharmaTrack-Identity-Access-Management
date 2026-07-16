package com.cts.pharmaTrack.module.identityAccessManagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

    private static final long RENEWAL_THRESHOLD_MS = 10 * 60 * 1000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Integer userId, Integer roleId, String role,
                                Integer siteId, String email) {
        return Jwts.builder()
                .setClaims(Map.of(
                        "userId", userId,
                        "roleId", roleId,
                        "role",   role,
                        "siteId", siteId,
                        "email",  email
                ))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Integer getUserId(String token) {
        return (Integer) parseClaims(token).get("userId");
    }

    public String getRole(String token) {
        return (String) parseClaims(token).get("role");
    }

    public String getEmail(String token) {
        return (String) parseClaims(token).get("email");
    }

    public Integer getRoleId(String token) {
        return (Integer) parseClaims(token).get("roleId");
    }

    public Integer getSiteId(String token) {
        return (Integer) parseClaims(token).get("siteId");
    }

    public boolean needsRenewal(String token) {
        Date expiry = parseClaims(token).getExpiration();
        long remaining = expiry.getTime() - System.currentTimeMillis();
        return remaining < RENEWAL_THRESHOLD_MS;
    }

    public String renewToken(String token) {
        Claims claims = parseClaims(token);
        return generateToken(
                (Integer) claims.get("userId"),
                (Integer) claims.get("roleId"),
                (String)  claims.get("role"),
                (Integer) claims.get("siteId"),
                (String)  claims.get("email")
        );
    }
}