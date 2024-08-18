package com.sa.clase.token.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public class TokenUtils {

    public static final String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    public static final Long ACCESS_TOKEN_LIFE = 86400000L; // 24 horas

    public static String createToken(String username, String email, Collection<? extends GrantedAuthority> roles) {
        long expirationTime = ACCESS_TOKEN_LIFE * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        List<String> rolesNames = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Map<String, Object> extra = new HashMap<>();
        extra.put("username", username);
        extra.put("roles", rolesNames);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.get("username", String.class);

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
        }
        return null;
    }
}
