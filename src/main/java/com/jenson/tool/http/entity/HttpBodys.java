package com.jenson.tool.http.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class HttpBodys {

    Map<String, Object> body = new HashMap<>();

    public HttpBodys add(String key,Object value){
        if (Objects.isNull(key)) {
            throw new NullPointerException("key is null");
        } else  {
            body.put(key, value);
        }
        return this;
    }

    public Object get(String key){
        return body.get(key);
    }

    public List<String> ketList(){
        return new ArrayList<>(body.keySet());
    }

    public Set<String> keySet(){
        return body.keySet();
    }
}
