package com.sa.clase.token.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
// se agrega para que sea un componente de Spring, un componente es un objeto que se instancia y se inyecta en otras clases
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Value("${acces_token_secret}")
    private String secretKeyToken;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entro a doFilterInternal");

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            Collection<GrantedAuthority> authorities = extractAuthoritiesFromToken(token);
            if (authorities != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Collection<GrantedAuthority> extractAuthoritiesFromToken(String token) {
        try {
            System.out.println("Entro a extractAuthoritiesFromToken");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKeyToken.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("Claims: " + claims);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            List<String> roles = (List<String>) claims.get("roles");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            System.out.println("Authorities: " + authorities);
            return authorities;
        } catch (Exception e) {
            return null; // En caso de error, devuelve null
        }
    }

}