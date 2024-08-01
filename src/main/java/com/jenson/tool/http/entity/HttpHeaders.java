package com.jenson.tool.http.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.*;


@Getter
@ToString
public class HttpHeaders {

    Map<String,String> header = new HashMap<>();

    public HttpHeaders add(String key,String value){
        if (Objects.isNull(key)) {
            throw new NullPointerException("key is null");
        } else if (Objects.isNull(value)) {
            throw new NullPointerException("value is null");
        } else {
            header.put(key, value);
        }
        return this;
    }

    public String get(String key){
        return header.get(key);
    }

    public List<String> ketList(){
        return new ArrayList<>(header.keySet());
    }

    public Set<String> keySet(){
        return header.keySet();
    }
}
