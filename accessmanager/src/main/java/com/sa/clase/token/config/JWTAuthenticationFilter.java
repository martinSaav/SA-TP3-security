package com.sa.clase.token.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.clase.token.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // se sobreescribe el metodo attemptAuthentication para que se ejecute cuando se intente autenticar
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();
        try {
            // se obtiene el cuerpo de la petición y se mapea a la clase AuthCredentials, en tiempo de ejecucion
            authCredentials = new ObjectMapper().readValue(request.getInputStream(), AuthCredentials.class);
        } catch (IOException e) {
        }

        // se crea la autenticación con el usuario y contraseña
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        System.out.println("Intentanto autenticar " + usernamePAT.getName() + " " + usernamePAT.getCredentials());

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    // se sobreescribe el metodo successfulAuthentication para que se ejecute cuando la autenticación sea exitosa
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        System.out.println("Autenticación exitosa");

        // se obtiene el usuario autenticado

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername(), userDetails.getAuthorities());

        System.out.println("Token: " + token);

        // se agrega el token al header de la respuesta

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Role", " " + userDetails.getAuthorities().toString());
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Error en la autenticación");
        response.getWriter().flush();
    }
}
