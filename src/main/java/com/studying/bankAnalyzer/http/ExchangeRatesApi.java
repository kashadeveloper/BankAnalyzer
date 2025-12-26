package com.studying.bankAnalyzer.http;

import com.studying.bankAnalyzer.dtos.ExchangeRatesResponse;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class ExchangeRatesApi {
    private final HttpClient client;
    private static final String API_URL = "https://openexchangerates.org/api";
    private final String appId;

    public ExchangeRatesApi(String appId) {
        client = HttpClient.newHttpClient();
        this.appId = appId;
    }

    public ExchangeRatesResponse GetCurrencyRates() {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(API_URL + "/latest.json?app_id=" + appId)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String stringBody = response.body();

            return new ObjectMapper().readValue(stringBody, ExchangeRatesResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
