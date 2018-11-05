package io.github.rxcats.reactivedemo.config;

import org.springframework.http.MediaType;

public class Constants {

    public static final String RXCATS_API_MEDIA_TYPE_VALUE = "application/vnd.rxcats.api";
    public static final MediaType RXCATS_API_MEDIA_TYPE = MediaType.valueOf(RXCATS_API_MEDIA_TYPE_VALUE);

    private Constants() {

    }
}
