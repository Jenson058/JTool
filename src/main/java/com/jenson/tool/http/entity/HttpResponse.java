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

    private BufferedReader bufferedReader;

    private HttpRequest httpRequest;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public <T> T body(Class<T> classOfT) {

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Gson().fromJson(sb.toString(), classOfT);
    }
}
