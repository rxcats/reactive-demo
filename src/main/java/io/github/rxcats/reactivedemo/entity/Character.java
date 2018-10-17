package io.github.rxcats.reactivedemo.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Document
public class Character {

    @Id
    ObjectId characterId;

    @Indexed(name = "i_name", unique = true)
    String name;

    String image;

    org.bson.Document data;

    LocalDateTime updatedAt;

    LocalDateTime createdAt;

}
