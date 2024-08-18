package com.sistemasactivos.webclient.controller;


import com.sistemasactivos.webclient.interfaces.IAccessManagerService;
import com.sistemasactivos.webclient.model.AuthCredentials;
import com.sistemasactivos.webclient.utils.TokenVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("")
public class AccessManagerController {

    @Autowired
    private IAccessManagerService accessManagerService;

    @Autowired
    private TokenVerificator tokenVerificator;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthCredentials authCredentials) {
        return accessManagerService.login(authCredentials)
                .map(token -> ResponseEntity.status(HttpStatus.OK)
                        .header("Authorization", "Bearer " + token)
                        .body("Autenticación exitosa"))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
