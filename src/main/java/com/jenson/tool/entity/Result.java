package com.jenson.tool.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> suc() {
        return new Result<T>()
                .setCode(200)
                .setMessage("调用成功");
    }

    public static <T> Result<T> suc(T t) {
        return new Result<T>()
                .setCode(200)
                .setMessage("调用成功")
                .setData(t);
    }

    public static <T> Result<T> suc(Integer code, T t) {
        return new Result<T>()
                .setCode(code)
                .setMessage("调用成功")
                .setData(t);
    }

    public static <T> Result<T> suc(Integer code, String message, T t) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setData(t);
    }

    public static <T> Result<T> err() {
        return new Result<T>()
                .setCode(500)
                .setMessage("调用失败");
    }

    public static <T> Result<T> err(Integer code) {
        return new Result<T>()
                .setCode(code);
    }

    public static <T> Result<T> err(Integer code, String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message);
    }
}
