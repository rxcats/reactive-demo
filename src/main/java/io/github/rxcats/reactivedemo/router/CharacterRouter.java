package io.github.rxcats.reactivedemo.router;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import lombok.extern.slf4j.Slf4j;

import io.github.rxcats.reactivedemo.handler.CharacterHandler;

@Slf4j
@Configuration
public class CharacterRouter {

    @Autowired
    CharacterHandler characterHandler;

    @Bean
    RouterFunction<?> routes() {
        return route()
            .GET("/v2/characters/{characterId}", characterHandler::getCharacter)
            .after((request, response) -> {
                log.info("request: {}", request);
                return response;
            })
            .build();
    }

}
