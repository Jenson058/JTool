package com.jenson.ajie.util.http;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public abstract class SuperHttp {

    private final String url;

    @Setter
    private Response response;

    private final Map<String, String> headers = new HashMap<>();

    public SuperHttp(String url) {
        this.url = url;
    }

    public SuperHttp addHeader(String key, String value) {
        if (Objects.isNull(key)) {
            throw new NullPointerException("key is null");
        } else if (Objects.isNull(value)) {
            throw new NullPointerException("value is null");
        } else {
            headers.put(key, value);
        }

        return this;
    }

    public abstract Response execute() throws IOException;
}
