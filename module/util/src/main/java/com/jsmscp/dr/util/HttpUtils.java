package com.jsmscp.dr.util;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtils {

    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postXml(String url, String xml) {
        return postContent(url, xml, MediaType.parse("application/xml; charset=utf-8"));
    }
    public static String postJson(String url, String json) {
        return postContent(url, json, MediaType.parse("application/json; charset=utf-8"));
    }

    private static String postContent(String url, String content, MediaType mediaType) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, content))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, String> map) {
        OkHttpClient  client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)// 连接超时 1000s
                .writeTimeout(2000, TimeUnit.SECONDS) // 输入超时 2000s
                .readTimeout(3000, TimeUnit.SECONDS) // 读取超时  3000s
                .build();

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url) //请求的url
                .post(formBody)
                .build();
        Response response;
        try  {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String queryInvoice(String url, String header){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).addHeader("Authorization", header).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
