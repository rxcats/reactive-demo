package io.github.rxcats.reactivedemo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.github.rxcats.reactivedemo.entity.Character;

public interface CharacterRepository extends ReactiveMongoRepository<Character, ObjectId> {

}
