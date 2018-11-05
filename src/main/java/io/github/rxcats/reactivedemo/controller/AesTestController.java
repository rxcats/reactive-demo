package io.github.rxcats.reactivedemo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping(consumes = "application/vnd.rxcats.api", produces = "application/vnd.rxcats.api")
@RestController
public class AesTestController {

    @PostMapping("/hello")
    public Mono<SomeType> hello(@RequestBody SomeType someType) {
        log.info("controller:{}", someType);
        return Mono.just(someType);
    }

}
