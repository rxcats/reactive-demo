package io.github.rxcats.reactivedemo.message;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import io.github.rxcats.reactivedemo.entity.Character;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CharacterRequest {

    @NotBlank
    String name;

    String image;

    org.bson.Document data;

    public Character toCharacter() {
        var character = new Character();
        character.setName(name);
        character.setImage(image);
        character.setData(data);
        character.setCreatedAt(LocalDateTime.now());
        character.setUpdatedAt(LocalDateTime.now());
        return character;
    }

    public Character toCharacter(Character old) {
        old.setName(name);
        old.setImage(image);
        old.setData(data);
        old.setUpdatedAt(LocalDateTime.now());
        return old;
    }

}
