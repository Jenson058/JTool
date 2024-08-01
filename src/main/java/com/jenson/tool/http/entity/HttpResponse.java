package com.jenson.tool.http.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Getter
@Setter
public class HttpResponse {

    private String body;

    private HttpRequest httpRequest;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String body() {
        return body;
    }
}
