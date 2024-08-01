package com.jenson.tool.http.auth;

import com.jenson.tool.http.entity.SuperHttp;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Post extends SuperHttp {

    private final Map<String, Object> body = new HashMap<>();
    private final Map<String, Object> form = new HashMap<>();

    public Post(String url) {
        super(url);
    }
}
