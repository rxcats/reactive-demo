package io.github.rxcats.reactivedemo.config;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.rxcats.reactivedemo.mvc.AesReader;
import io.github.rxcats.reactivedemo.mvc.AesWriter;
import io.github.rxcats.reactivedemo.serializer.LocalDateTimeSerializer;
import io.github.rxcats.reactivedemo.serializer.ObjectIdDeSerializer;
import io.github.rxcats.reactivedemo.serializer.ObjectIdSerializer;

@Configuration
public class WebFluxConfig {

    @Bean
    SimpleModule simpleModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(ObjectId.class, new ObjectIdSerializer());
        simpleModule.addDeserializer(ObjectId.class, new ObjectIdDeSerializer());
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        return simpleModule;
    }

    @Bean
    ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
            .featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(simpleModule()).build();
    }

    @Bean
    Jackson2JsonEncoder jackson2JsonEncoder() {
        return new Jackson2JsonEncoder(objectMapper());
    }

    @Bean
    Jackson2JsonDecoder jackson2JsonDecoder() {
        return new Jackson2JsonDecoder(objectMapper());
    }

    @Bean
    AesReader aesReader() {
        return new AesReader(objectMapper());
    }

    @Bean
    AesWriter aesWriter() {
        return new AesWriter(objectMapper());
    }

    @Bean
    WebFluxConfigurer webFluxConfigurer(Jackson2JsonEncoder encoder,
                                        Jackson2JsonDecoder decoder,
                                        AesReader aesReader,
                                        AesWriter aesWriter) {
        return new WebFluxConfigurer() {

            @Override
            public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
                configurer.defaultCodecs().jackson2JsonEncoder(encoder);
                configurer.defaultCodecs().jackson2JsonDecoder(decoder);
                configurer.customCodecs().reader(aesReader);
                configurer.customCodecs().writer(aesWriter);
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
