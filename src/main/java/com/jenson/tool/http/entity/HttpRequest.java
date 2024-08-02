package com.jenson.tool.http.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Getter
@Accessors(chain = true)
public class HttpRequest {

    private HttpURLConnection connection;

    private InputStream inputStream;

    private BufferedReader bufferedReader;

    @Setter
    private String url;

    @Setter
    private Integer connectTimeout = 500;

    @Setter
    private Integer readTimeout = 500;

    @Setter
    private HttpHeaders headers = new HttpHeaders();

    @Setter
    private HttpBodys bodys = new HttpBodys();

    @Setter
    private HttpForms forms = new HttpForms();

    private final HttpResponse httpResponse = new HttpResponse(this);


    private final static String GET = "GET";
    private final static String POST = "POST";

    public void before(String method) {
        try {
            URL u = new URL(url);

            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod(GET);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8;");

            if (Objects.nonNull(headers.getHeader())) {
                headers.getHeader()
                        .forEach((key, value) -> {
                            connection.setRequestProperty(key, value);
                        });
            }

        } catch (IOException e) {
            e.printStackTrace();
            close();
        }

    }

    public void execute() {
        try {
            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            httpResponse.setBody(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public HttpResponse get() {
        before(GET);
        try {
            connection.connect();
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return httpResponse;
    }

    public HttpResponse post() {

        before(POST);
        try {

            //设置成true，向远程服务器写数据
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.
                    setRequestProperty("Accept", "application/json");
            String body = new Gson().toJson(bodys);
            OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
            outputStream.write(body);
            outputStream.flush();
            outputStream.close();

            connection.connect();
            execute();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
        return httpResponse;

    }

    public void close() {
        try {
            if (Objects.nonNull(connection)) {
                connection.disconnect();
            }
            if (Objects.nonNull(bufferedReader)) {
                bufferedReader.close();
            }
            if (Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
