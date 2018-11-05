package io.github.rxcats.reactivedemo.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import io.github.rxcats.reactivedemo.config.Constants;
import reactor.core.publisher.Mono;

@Slf4j
public class AesWriter implements HttpMessageWriter<Object> {

    private final ObjectMapper mapper;

    public AesWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<MediaType> getWritableMediaTypes() {
        return List.of(Constants.RXCATS_API_MEDIA_TYPE);
    }

    @Override
    public boolean canWrite(ResolvableType elementType, MediaType mediaType) {
        return true;
    }

    @Override
    public Mono<Void> write(Publisher<?> inputStream, ResolvableType elementType, MediaType mediaType, ReactiveHttpOutputMessage message, Map<String, Object> hints) {
        return Mono.empty().then();
    }

    @Override
    public Mono<Void> write(Publisher<?> inputStream, ResolvableType actualType, ResolvableType elementType, MediaType mediaType, ServerHttpRequest request, ServerHttpResponse response, Map<String, Object> hints) {

        Mono<DataBuffer> map = Mono.from(inputStream)
            .map(value -> {
                String content = "";
                try {
                    content = mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return content;
            }).map(value -> {
                DataBuffer buffer = response.bufferFactory().allocateBuffer();
                OutputStream stream = buffer.asOutputStream();

                try {
                    stream.write(value.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HttpHeaders headers = response.getHeaders();
                headers.setContentLength(buffer.readableByteCount());

                log.info("write body content:{}", value);

                return buffer;
            });

        return response.writeWith(map);
    }
}
