package com.stockpriceviewer;


import com.stockpriceviewer.Market.client.FinnhubClient;
import com.stockpriceviewer.Market.client.FinnhubQuoteResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
@Getter
@Setter
public class StockService {
    private final StockRepository stockRepository;
    private final FinnhubClient finnhubClient;


    public StockService(StockRepository stockRepository, FinnhubClient finnhubClient) {
        this.stockRepository = stockRepository;
        this.finnhubClient = finnhubClient;
    }


    public StockEntity createStock(String ticker) {
        ticker = ticker.trim().toUpperCase();

        FinnhubQuoteResponse quote = finnhubClient
                .getStockQuote(ticker)
                .block();

        StockEntity stock = new StockEntity();
        stock.setTicker(ticker);
        stock.setCompanyName(ticker);
        stock.setCurrentPrice(quote.getCurrentPrice());
        stock.setLastUpdated(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    public List<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }


    public StockEntity refreshStock(String ticker) {
        ticker = ticker.trim().toUpperCase();
        StockEntity stock = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new StockNotFoundException("Stock not found!"));

        FinnhubQuoteResponse quote = finnhubClient
                .getStockQuote(ticker)
                .block();

        stock.setCurrentPrice(quote.getCurrentPrice());
        stock.setLastUpdated(LocalDateTime.now());

        return stockRepository.save(stock);
    }
    public void deleteStock(String ticker) {
        ticker = ticker.trim().toUpperCase();
        StockEntity stockTicket = stockRepository.findByTicker(ticker).orElseThrow(() -> new StockNotFoundException("Stock not found!"));
        stockRepository.delete(stockTicket);
    }

    public StockEntity getStockByTicker(String ticker) {
        return stockRepository.findByTicker(ticker).orElseThrow(() -> new StockNotFoundException(ticker));
    }
}
