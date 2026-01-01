package portfolio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/api/stocks/{ticket}")
    public ResponseEntity<StockResponseDTO> createStock(@PathVariable String ticket) {
        StockEntity stock = stockService.createStock(ticket);
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

    private StockResponseDTO mapToDto(StockEntity stock) {
        return new StockResponseDTO(
                stock.getTicket(),
                stock.getCompanyName(),
                stock.getCurrentPrice(),
                stock.getLastUpdated()
        );
    }
}
