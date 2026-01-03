package com.stockpriceviewer;


import com.stockpriceviewer.Market.client.FinnhubClient;
import com.stockpriceviewer.Market.client.FinnhubQuoteResponse;
import com.stockpriceviewer.portfolio.ENUM.AssetType;
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

    public StockEntity addStock(String ticker) {
        return upsertAsset(ticker, AssetType.STOCK);
    }

    public StockEntity addCrypto(String symbol) {
        return upsertAsset(symbol, AssetType.CRYPTO);
    }


    public List<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }


    public StockEntity refreshAsset(String symbol, AssetType assetType) {
        symbol = normalize(symbol, assetType);

        StockEntity asset = stockRepository.findByTicker(symbol)
                .orElseThrow(() -> new StockNotFoundException("Asset not found: " + assetType));

        FinnhubQuoteResponse quote = requireQuote(symbol);

        asset.setCurrentPrice(quote.getCurrentPrice());
        asset.setLastUpdated(LocalDateTime.now());

        return stockRepository.save(asset);

    }

    public void delete(String ticker) {
        ticker = ticker.trim();
        String finalTicker = ticker;
        StockEntity asset = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new StockNotFoundException("Asset not found: " + finalTicker));
        stockRepository.delete(asset);
    }

    public StockEntity getByTicker(String ticker) {
        return stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new StockNotFoundException("Asset not found: " + ticker));
    }

    public StockEntity upsertAsset(String symbol, AssetType type) {
        symbol = symbol == null ? null : symbol.trim();

        StockEntity asset = stockRepository.findByTicker(symbol)
                .orElseGet(StockEntity::new);

        FinnhubQuoteResponse quote = finnhubClient.getStockQuote(symbol).block();

        asset.setTicker(symbol);
        asset.setCompanyName(symbol);
        asset.setCurrentPrice(quote.getCurrentPrice());
        asset.setLastUpdated(LocalDateTime.now());
        asset.setAssetType(type);

        return stockRepository.save(asset);
    }


    private String normalize(String symbol, AssetType type) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol cannot be null");
        }

        symbol = symbol.trim();
        if (type == AssetType.STOCK) {
            symbol = symbol.toUpperCase();
        }

        return symbol;

    }

    private FinnhubQuoteResponse requireQuote(String symbol) {
        FinnhubQuoteResponse quote = finnhubClient.getStockQuote(symbol).block();

        if (quote == null || quote.getCurrentPrice() == null) {
            throw new IllegalArgumentException(
                    "Finnhub returned no price data for symbol: " + symbol
            );
        }

        return quote;
    }
}
