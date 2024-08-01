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
    private HttpHeaders headers;

    @Setter
    private HttpBodys bodys;

    @Setter
    private HttpForms forms;

    private final HttpResponse httpResponse = new HttpResponse(this);


    private final static String GET = "GET";
    private final static String POST = "POST";

    public HttpResponse get() {
        try {
            URL u = new URL(url);

            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod(GET);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8;");

            headers.getHeader()
                    .forEach((key, value) -> {
                        connection.setRequestProperty(key, value);
                    });

            connection.connect();

            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            httpResponse.setBufferedReader(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return httpResponse;
    }

    public HttpResponse post() throws IOException {

        try {
            URL u = new URL(url);

            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod(POST);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8;");

            headers.getHeader()
                    .forEach((key, value) -> {
                        connection.setRequestProperty(key, value);
                    });

            connection.connect();

            String body = new Gson().toJson(bodys);

            //设置成true，向远程服务器写数据
            connection.setDoOutput(true);//默认是false，无法写入body
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();
            outputStream.close();

            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            httpResponse.setBufferedReader(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return httpResponse;

    }


}
