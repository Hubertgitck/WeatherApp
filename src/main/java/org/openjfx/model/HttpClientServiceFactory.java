package org.openjfx.model;

import org.openjfx.model.httpClient.OkHttp;
import org.openjfx.model.httpClient.iHttpClient;

public class HttpClientServiceFactory {

    public static HttpClientService createHttpClientService(){
        return new HttpClientService(createHttpClient());
    }

    private static iHttpClient createHttpClient() {
        return new OkHttp();
    }
}
