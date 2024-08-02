package com.jenson.tool.http.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用Http父类
 *
 * <p>@author Jenson </p>
 * <p>@date 2024-08-02 </p>
 */
@Getter
public abstract class SuperHttp {

    /**
     * 请求链接
     */
    private final String url;

    /**
     * 请求头
     */
    @Setter
    private HttpHeaders headers = new HttpHeaders();

    public SuperHttp(String url) {
        this.url = url;
    }

    /**
     * 增加请求头信息
     *
     * @param key key
     * @param value value
     */
    public void addHeader(String key, String value) {
        headers.add(key, value);
    }
}
