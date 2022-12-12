package org.openjfx.model;

import org.openjfx.model.httpClient.iHttpClient;

public class HttpClientService {

    private final iHttpClient iHttpClient;


    public HttpClientService(iHttpClient iHttpClient) {
        this.iHttpClient = iHttpClient;
    }

    public String getResponseAsJSONString(String urlString){
        return iHttpClient.getResponseFromApiAsJSONString(urlString);
    }
}
