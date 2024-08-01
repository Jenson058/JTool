package com.jenson.tool.http.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jenson
 */
@Getter
@NoArgsConstructor
public class Http {

    public Get get(String url) {
        return new Get(url);
    }

    public Post post(String url) {
        return new Post(url);
    }
}
