package io.github.rxcats.reactivedemo.controller;

import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.rxcats.reactivedemo.exception.ServiceException;
import io.github.rxcats.reactivedemo.message.CharacterRequest;
import io.github.rxcats.reactivedemo.message.ResponseEntity;
import io.github.rxcats.reactivedemo.message.ResultCode;
import io.github.rxcats.reactivedemo.repository.CharacterRepository;
import reactor.core.publisher.Mono;

@RestController
public class CharacterController {

    private final CharacterRepository repository;

    @Autowired
    public CharacterController(CharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/characters/{characterId}")
    public Mono<ResponseEntity> getCharacter(@PathVariable ObjectId characterId) {
        return repository.findById(characterId)
            .flatMap(c -> Mono.just(ResponseEntity.of(c)));
    }

    @GetMapping("/characters")
    public Mono<ResponseEntity> getAllCharacters() {
        return repository.findAll()
            .collectList()
            .flatMap(list -> Mono.just(ResponseEntity.of(list)));
    }

    @DeleteMapping("/characters/{characterId}")
    public Mono<ResponseEntity> remove(@PathVariable ObjectId characterId) {
        return repository.deleteById(characterId)
            .flatMap(r -> Mono.just(ResponseEntity.of()));
    }

    @PutMapping("/characters/{characterId}")
    public Mono<ResponseEntity> editCharacter(@PathVariable ObjectId characterId, @RequestBody CharacterRequest request) {
        return repository.findById(characterId)
            .doOnNext(c -> {
                if (Objects.isNull(c)) {
                    throw new ServiceException(ResultCode.error, "could not find character:" + characterId);
                }
            })
            .flatMap(old -> Mono.just(request.toCharacter(old)))
            .doOnNext(repository::save)
            .flatMap(c -> Mono.just(ResponseEntity.of(c)));
    }

}
