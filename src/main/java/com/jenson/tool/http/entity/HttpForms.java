package com.jenson.tool.http.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.*;


@Getter
@ToString
public class HttpForms {

    Map<String, Object> form;

    public HttpForms add(String key, Object value) {
        if (Objects.isNull(key)) {
            throw new NullPointerException("key is null");
        } else {
            form.put(key, value);
        }
        return this;
    }

    public Object get(String key) {
        return form.get(key);
    }

    public List<String> ketList() {
        return new ArrayList<>(form.keySet());
    }

    public Set<String> keySet() {
        return form.keySet();
    }

}
