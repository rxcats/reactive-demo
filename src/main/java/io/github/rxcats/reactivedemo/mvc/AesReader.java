package io.github.rxcats.reactivedemo.config;

import static org.springframework.core.io.buffer.DataBufferUtils.join;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class AesReader implements HttpMessageReader<Object> {

    private final ObjectMapper mapper;

    public AesReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<MediaType> getReadableMediaTypes() {
        return List.of(MediaType.valueOf("application/vnd.rxcats.api"));
    }

    @Override
    public boolean canRead(ResolvableType elementType, MediaType mediaType) {
        return true;
    }

    @Override
    public Flux<Object> read(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints) {
        return null;
    }

    @Override
    public Mono<Object> readMono(ResolvableType elementType, ReactiveHttpInputMessage message, Map<String, Object> hints) {
        return join(message.getBody())
            .map(dataBuffer -> {
                String content = "";
                try {
                    content = IOUtils.toString(dataBuffer.asInputStream(), StandardCharsets.UTF_8);
                    log.info("read body content:{}", content);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Object o = null;

                try {
                    o = mapper.readValue(content, Objects.requireNonNull(elementType.getRawClass()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return o;
            });
    }
}
