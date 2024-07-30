package com.jenson.ajie.util.http;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jenson
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Http {

    public Get get(String url) {
        return new Get(url);
    }

    public Post post(String url) {
        return new Post(url);
    }
}
