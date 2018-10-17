package io.github.rxcats.reactivedemo;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

import io.github.rxcats.reactivedemo.repository.CharacterRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.github.rxcats.reactivedemo.entity.Character;
import reactor.test.StepVerifier;

@Slf4j
@DataMongoTest
@RunWith(SpringRunner.class)
public class CharacterRepositoryTest {

    @Autowired
    CharacterRepository repository;

    private Character create() {
        var character = new Character();
        character.setName("테스트:" + UUID.randomUUID());
        character.setCreatedAt(LocalDateTime.now());
        character.setUpdatedAt(LocalDateTime.now());
        return character;
    }

    @Test
    public void insertTest() {
        Flux<Character> inserted = repository.insert(Mono.just(create()));

        StepVerifier.create(inserted)
            .assertNext(c -> {
                log.info("character:{}", c);
                assertThat(c.getCharacterId()).isNotNull();
            })
            .verifyComplete();

    }

}
