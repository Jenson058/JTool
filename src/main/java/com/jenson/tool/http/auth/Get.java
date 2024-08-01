package com.jenson.tool.http.auth;


import com.jenson.tool.http.entity.HttpHeaders;
import com.jenson.tool.http.entity.HttpRequest;
import com.jenson.tool.http.entity.HttpResponse;
import com.jenson.tool.http.entity.SuperHttp;

public class Get extends SuperHttp {

    private final HttpRequest httpRequest = new HttpRequest();

    public Get(String url) {
        super(url);
    }

    @Override
    public Get addHeader(String key, String value) {
        super.addHeader(key, value);
        return this;
    }

    public HttpResponse execute() {
        return httpRequest.setUrl(getUrl())
                .setHeaders(super.getHeaders())
                .get();
    }
}
