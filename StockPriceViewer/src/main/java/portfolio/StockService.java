package portfolio;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Getter
@Setter
public class StockService {
    private final StockRepository stockRepository;


    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public StockEntity createStock(String ticket) {
        StockEntity stock = new StockEntity();
        stock.setTicker(ticket);
        stock.setCompanyName(ticket);
        stock.setCurrentPrice(new BigDecimal("500.00"));
        stock.setLastUpdated(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    public List<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }


    public StockEntity refreshStock(String ticket) {
        StockEntity stockTicket = stockRepository.findByTicket(ticket).orElseThrow(() -> new RuntimeException("Stock not found!"));
        BigDecimal updatePrice = stockTicket.getCurrentPrice().add(new BigDecimal("530.32"));
        stockTicket.setCurrentPrice(updatePrice);
        stockTicket.setLastUpdated(LocalDateTime.now());
        return stockRepository.save(stockTicket);

    }

    public void deleteStock(String ticket) {
        StockEntity stockTicket = stockRepository.findByTicket(ticket).orElseThrow(() -> new RuntimeException("Stock not found!"));
        stockRepository.delete(stockTicket);
    }

    public StockEntity getStockByTicker(String ticker) {
        return stockRepository.findByTicket(ticker).orElseThrow(() -> new StockNotFoundException(ticker));
    }
}
