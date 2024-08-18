package com.sistemasactivos.webclient.interfaces;

import com.sistemasactivos.webclient.model.AuthCredentials;
import reactor.core.publisher.Mono;

public interface IAccessManagerService {

    Mono<String> login(AuthCredentials authCredentials);
}
