package io.github.rxcats.reactivedemo.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.github.rxcats.reactivedemo.message.ResponseEntity;
import io.github.rxcats.reactivedemo.repository.CharacterRepository;
import reactor.core.publisher.Mono;

@Component
public class CharacterHandler {

    @Autowired
    CharacterRepository repository;

    public Mono<ServerResponse> getCharacter(ServerRequest request) {
        var characterId = new ObjectId(request.pathVariable("characterId"));
        return repository.findById(characterId)
            .flatMap(character -> ok().body(fromObject(ResponseEntity.of(character))))
            .switchIfEmpty(ok().body(fromObject(ResponseEntity.of())));
    }

}
