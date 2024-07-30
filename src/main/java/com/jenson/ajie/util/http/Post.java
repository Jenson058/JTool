package com.jenson.ajie.util.http;

import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.internal.http2.Header;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Post extends SuperHttp {

    private final Map<String, Object> body = new HashMap<>();
    private final Map<String, Object> form = new HashMap<>();

    public Post(String url) {
        super(url);
    }

    @Override
    public Post addHeader(String key, String value) {
        return (Post) super.addHeader(key, value);
    }

    @Override
    public Response execute() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(body));

        Request request = new Request
                .Builder()
                .post(requestBody)
                .url(getUrl())
                .headers(Headers.of(getHeaders()))
                .build();

        setResponse(client.newCall(request).execute());
        return getResponse();
    }

    public Post addBody(String key, Object value) {
        if (!form.isEmpty()) {
            throw new NullPointerException("form not empty");
        }
        if (Objects.isNull(key)) {
            throw new NullPointerException("key is null");
        } else if (Objects.isNull(value)) {
            throw new NullPointerException("value is null");
        } else {
            body.put(key, value);
        }
        return this;
    }

    public Post addForm(String key, Object value) {
        if (!body.isEmpty()) {
            throw new NullPointerException("body not empty");
        }
        if (Objects.isNull(key)) {
            throw new NullPointerException("key is null");
        } else if (Objects.isNull(value)) {
            throw new NullPointerException("value is null");
        } else {
            form.put(key, value);
        }
        return this;
    }
}
