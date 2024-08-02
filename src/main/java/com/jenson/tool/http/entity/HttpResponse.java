package com.jenson.tool.http.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jenson.tool.entity.Result;
import lombok.Getter;
import lombok.Setter;


/**
 * <p>请求返回类</p>
 *
 * <p>@author Jenson </p>
 * <p>@date 2024-08-02 </p>
 */
@Getter
@Setter
public class HttpResponse {


    /**
     * 接收参数体
     */
    private String body;

    /**
     * 请求发起类
     */
    private HttpRequest httpRequest;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * 返回请求体字符串
     * @return body
     */
    public String body() {
        return body;
    }

    /**
     * 返回请求体对象
     *
     * @param tClass class
     * @param <T> 需要返回对象的类型
     * @return body as object
     */
    public <T> T body(Class<T> tClass) {
        return new Gson().fromJson(body,tClass);
    }

    /**
     *  返回请求体对象一般用于枚举类型
     *
     * <p>
     * 使用方法:
     * httpResponse.body(new TypeToken<你需要的枚举类型>(){})
     * </p>
     *
     * @param typeToken 枚举对象容器
     * @param <T> 枚举对象
     * @return body as object
     */
    public <T> T body(TypeToken<?> typeToken) {
        return new Gson().fromJson(body,typeToken.getType());
    }
}
