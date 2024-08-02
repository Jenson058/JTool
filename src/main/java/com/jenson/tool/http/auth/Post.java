package com.jenson.tool.http.auth;

import com.jenson.tool.http.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post extends SuperHttp {

    private final HttpRequest httpRequest = new HttpRequest();
    private final HttpBodys body = new HttpBodys();
//    private final HttpForms form = new HttpForms();

    public Post(String url) {
        super(url);
    }

    @Override
    public void addHeader(String key, String value) {
        super.addHeader(key, value);
    }

    public Post addBody(String key,Object value) {
        body.add(key, value);
        return this;
    }



    public HttpResponse execute() {
        return httpRequest.setUrl(getUrl())
                .setHeaders(super.getHeaders())
                .setBodys(body)
                .post();
    }
}
