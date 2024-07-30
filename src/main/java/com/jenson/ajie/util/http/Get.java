package com.jenson.ajie.util.http;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Get extends SuperHttp {

    public Get(String url) {
        super(url);
    }

    @Override
    public Get addHeader(String key, String value) {
        return (Get) super.addHeader(key, value);
    }

    @Override
    public Response execute() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();

        Request request = new Request
                .Builder()
                .get()
                .url(getUrl())
                .headers(Headers.of(getHeaders()))
                .build();

        setResponse(client.newCall(request).execute());

        return this.getResponse();
    }
}
