package com.sistemasactivos.webclient.config;


import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;


@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {


    @Value("${api.base-url-1}")
    private String baseUrl1;

    @Value("${api.username-1}")
    private String username1;

    @Value("${api.password-1}")
    private String password1;


    @Bean
    @Qualifier("webClientUser")
    public WebClient getWebClientUser(){
        return createWebClient("https://localhost:444/","user", "123");
    }

    @Bean
    @Qualifier("healthPlanWebClient")
    public WebClient getWebClient1() {
        return createWebClient(baseUrl1, username1, password1);
    }

    private WebClient createWebClient(String url, String user, String password){
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(tcpClient ->
                        tcpClient
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(connection ->
                                        connection.addHandlerLast(new ReadTimeoutHandler(10))
                                                .addHandlerLast(new WriteTimeoutHandler(10))))
                .secure(sslContextSpec ->
                        sslContextSpec
                                .sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeaders(headers -> headers.setBasicAuth(user, password))
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /*

    private ExchangeFilterFunction tokenVerificationFilter(CacheManager cacheManager) {
        return (clientRequest, next) -> {
            String token = cacheManager.getCache("auth").get("token", String.class);
            if (token == null) {
                throw new IllegalStateException("Token not found in cache");
            }
            return next.exchange(clientRequest);

            if (!clientRequest.headers().containsKey("Authorization-token")) {
                throw new IllegalStateException("Token not found in request");
            }

            if (clientRequest.headers().get("Authorization-token").get(0).equals("Bearer " + token)) {
                return next.exchange(clientRequest);
            } else {
                throw new IllegalStateException("Token not valid");
            }


        };
    }

    private ExchangeFilterFunction noVerificationFilter() {
        return (clientRequest, next) -> next.exchange(clientRequest);
    }

    */
}


