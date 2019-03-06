package io.github.rxcats.reactivedemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.rxcats.reactivedemo.entity.Character;
import io.github.rxcats.reactivedemo.exception.ServiceException;
import io.github.rxcats.reactivedemo.message.CharacterRequest;
import io.github.rxcats.reactivedemo.message.ResponseEntity;
import io.github.rxcats.reactivedemo.message.ResultCode;
import io.github.rxcats.reactivedemo.repository.CharacterRepository;
import reactor.core.publisher.Mono;

@RestController
public class CharacterController {

    @Autowired
    private CharacterRepository repository;

    @GetMapping("/characters/{characterId}")
    public Mono<ResponseEntity<Character>> getCharacter(@PathVariable ObjectId characterId) {
        return repository.findById(characterId)
            .flatMap(r -> Mono.just(ResponseEntity.of(r)))
            .switchIfEmpty(Mono.just(ResponseEntity.of()));
    }

    @GetMapping("/characters")
    public Mono<ResponseEntity<List<Character>>> getAllCharacters() {
        return repository.findAll()
            .collectList()
            .flatMap(list -> Mono.just(ResponseEntity.of(list)))
            .switchIfEmpty(Mono.just(ResponseEntity.of()));
    }

    @DeleteMapping("/characters/{characterId}")
    public Mono<ResponseEntity> remove(@PathVariable ObjectId characterId) {
        return repository.deleteById(characterId)
            .then(Mono.just(ResponseEntity.of()));
    }

    @PostMapping("/characters")
    public Mono<ResponseEntity<Character>> addCharacter(@Valid @RequestBody CharacterRequest request) {
        return repository.insert(request.toCharacter())
            .flatMap(r -> Mono.just(ResponseEntity.of(r)))
            .switchIfEmpty(Mono.just(ResponseEntity.of()));
    }

    @PutMapping("/characters/{characterId}")
    public Mono<ResponseEntity<Character>> editCharacter(@PathVariable ObjectId characterId, @Valid @RequestBody CharacterRequest request) {
        return repository.findById(characterId)
            .flatMap(old -> repository.save(request.toCharacter(old)).flatMap((r -> Mono.just(ResponseEntity.of(r)))))
            .switchIfEmpty(Mono.error(new ServiceException(ResultCode.error, "could not find character:" + characterId)));
    }

}
