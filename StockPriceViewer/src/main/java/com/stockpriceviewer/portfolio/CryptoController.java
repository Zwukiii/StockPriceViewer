package com.stockpriceviewer.portfolio;

import com.stockpriceviewer.StockEntity;
import com.stockpriceviewer.StockResponseDTO;
import com.stockpriceviewer.StockService;
import com.stockpriceviewer.portfolio.ENUM.AssetType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final StockService stockService;


    public CryptoController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/{symbol}")
    public ResponseEntity<StockResponseDTO> addCrypto(@PathVariable String symbol) {
        StockEntity crypto = stockService.addCrypto(symbol);
        return  ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(crypto));

    }


    @PutMapping("/{symbol}/refresh")
    public ResponseEntity<StockResponseDTO> refreshCrypto(@PathVariable String symbol) {
        StockEntity refresh = stockService.refreshAsset(symbol, AssetType.CRYPTO);
        return  ResponseEntity.ok(mapToDto(refresh));
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<StockResponseDTO> getCrypto(@PathVariable String symbol) {
        StockEntity stock = stockService.getByTicker(symbol);
        return ResponseEntity.ok(mapToDto(stock));
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> deleteCrypto(@PathVariable String symbol) {
        stockService.delete(symbol);
        return ResponseEntity.noContent().build();
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
