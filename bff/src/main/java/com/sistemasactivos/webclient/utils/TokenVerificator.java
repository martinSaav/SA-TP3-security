package com.sistemasactivos.webclient.utils;

import com.sistemasactivos.webclient.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TokenVerificator {

    @Autowired
    private CacheManager cacheManager;

    public void verifyToken(String token) {
        String tokenCache = cacheManager.getCache("auth").get("token", String.class);
        token = token.replace("Bearer ", "");
        if (tokenCache == null) {
            throw new BusinessException("Token no encontrado", HttpStatus.UNAUTHORIZED);
        }
        if (!token.equals(tokenCache)) {
            throw new BusinessException("Token no valido", HttpStatus.UNAUTHORIZED);
        }
    }
}
