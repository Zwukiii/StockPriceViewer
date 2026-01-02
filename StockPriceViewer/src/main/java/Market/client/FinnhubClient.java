package Market.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FinnhubClient {

    private final WebClient webClient;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public FinnhubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getStockPrice(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quote")
                        .queryParam("symbol", symbol)
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}