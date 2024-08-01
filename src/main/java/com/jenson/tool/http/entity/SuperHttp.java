package com.jenson.tool.http.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;

@Getter
public abstract class SuperHttp {

    private final String url;

    @Setter
    private HttpHeaders headers = new HttpHeaders();

    public SuperHttp(String url) {
        this.url = url;
    }

    public SuperHttp addHeader(String key, String value) {
        headers.add(key, value);
        return this;
    }
}
