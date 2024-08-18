package com.sistemasactivos.webclient.service;


import com.sistemasactivos.webclient.interfaces.IAccessManagerService;
import com.sistemasactivos.webclient.model.AuthCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class AccessManagerService implements IAccessManagerService {

    @Autowired
    @Qualifier("webClientUser")
    private WebClient webClient;

    @Autowired
    CacheManager cacheManager;

    @Override
    public Mono<String> login(AuthCredentials authCredentials) {
        return webClient.post()
                .uri("/login")
                .contentType(APPLICATION_JSON)
                .body(Mono.just(authCredentials), AuthCredentials.class)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        Optional<String> authResponse = clientResponse.headers().asHttpHeaders().get("Authorization").stream().findFirst();
                        if (authResponse.isPresent()) {
                            String token = authResponse.get().replace("Bearer ", "");
                            cacheManager.getCache("auth").put("token", token);
                            return Mono.just(token);
                        }
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.bodyToMono(String.class).flatMap(error -> Mono.error(new Exception(error)));
                    }
                });
    }

}
