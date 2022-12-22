package org.openjfx.model.httpClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttp implements iHttpClient{

    private final OkHttpClient client;

    public OkHttp(OkHttpClient client) {
        this.client = client;
    }

    public String getResponseFromApiAsJSONString(String url){
        Request request = requestBuilder(url);
        Response response = getResponseFromRequest(request);

        try {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("Failed to getCityData for %s".formatted(url), e);
        }
    }

    private Response getResponseFromRequest(Request request){
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("Failed to response", e);
        }
    }

    private Request requestBuilder(String urlString){
        return new Request.Builder()
                .url(urlString)
                .build();
    }
}
