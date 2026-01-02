package com.stockpriceviewer.Market.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FinnhubClient {

    private final WebClient webClient;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public FinnhubClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://finnhub.io/api/v1")
                .build();
    }

    public Mono<FinnhubQuoteResponse> getStockQuote(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quote")
                        .queryParam("symbol", symbol)
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(FinnhubQuoteResponse.class);
    }
}
