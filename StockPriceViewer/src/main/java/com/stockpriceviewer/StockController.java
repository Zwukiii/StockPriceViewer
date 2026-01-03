package com.stockpriceviewer;
import com.stockpriceviewer.portfolio.ENUM.AssetType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/{ticker}")
    public ResponseEntity<StockResponseDTO> createStock(@PathVariable String ticker) {
        StockEntity stock = stockService.addStock(ticker);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(stock));
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> getAllStocks() {
        List<StockResponseDTO> stocks = stockService.getAllStocks()
                .stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(stocks);
    }


    @PutMapping("/{ticker}/refresh")
    public ResponseEntity<StockResponseDTO> refreshStock(@PathVariable String ticker) {
        StockEntity stock = stockService.refreshAsset(ticker, AssetType.STOCK);
        return ResponseEntity.ok(mapToDto(stock));
    }


    @DeleteMapping("/{ticker}")
    public ResponseEntity<Void> deleteStock(@PathVariable String ticker) {
         stockService.delete(ticker);
         return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<StockResponseDTO> getSingleStock(@PathVariable String ticker) {
        StockEntity stock = stockService.getByTicker(ticker);
        return ResponseEntity.ok(mapToDto(stock));
    }


    private StockResponseDTO mapToDto(StockEntity stock) {
        return new StockResponseDTO(
                stock.getTicker(),
                stock.getCompanyName(),
                stock.getCurrentPrice(),
                stock.getLastUpdated()
        );
    }
}
