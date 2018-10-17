package io.github.rxcats.reactivedemo.controller;

import java.util.Objects;

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
            .map(ResponseEntity::of);
    }

    @GetMapping("/characters")
    public Mono<ResponseEntity> getAllCharacters() {
        return repository.findAll()
            .collectList()
            .map(ResponseEntity::of);
    }

    @DeleteMapping("/characters/{characterId}")
    public Mono<ResponseEntity> remove(@PathVariable ObjectId characterId) {
        return repository.deleteById(characterId)
            .map(ResponseEntity::of);
    }

    @PostMapping("/characters")
    public Mono<ResponseEntity> addCharacter(@Valid @RequestBody CharacterRequest request) {
        return repository.insert(request.toCharacter())
            .map(ResponseEntity::of);
    }

    @PutMapping("/characters/{characterId}")
    public Mono<ResponseEntity> editCharacter(@PathVariable ObjectId characterId, @Valid @RequestBody CharacterRequest request) {
        return repository.findById(characterId)
            .flatMap(old -> {
                if (Objects.isNull(old)) {
                    throw new ServiceException(ResultCode.error, "could not find character:" + characterId);
                }
                return repository.save(request.toCharacter(old));
            })
            .map(ResponseEntity::of);
    }

}
