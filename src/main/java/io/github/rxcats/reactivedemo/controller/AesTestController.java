package io.github.rxcats.reactivedemo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import io.github.rxcats.reactivedemo.config.Constants;
import io.github.rxcats.reactivedemo.message.AesReqRes;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping(consumes = Constants.RXCATS_API_MEDIA_TYPE_VALUE, produces = Constants.RXCATS_API_MEDIA_TYPE_VALUE)
@RestController
public class AesTestController {

    @PostMapping("/hello")
    public Mono<AesReqRes> hello(@RequestBody AesReqRes req) {
        log.info("request:{}", req);
        return Mono.just(req);
    }

}
