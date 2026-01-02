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


    public StockEntity createStock(String ticker) {
        StockEntity stock = new StockEntity();
        stock.setTicker(ticker);
        stock.setCompanyName(ticker);
        stock.setCurrentPrice(new BigDecimal());
        stock.setLastUpdated(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    public List<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }


    public StockEntity refreshStock(String ticker) {
        StockEntity stockTicket = stockRepository.findByTicket(ticker).orElseThrow(() -> new StockNotFoundException("Stock not found!"));
        BigDecimal updatePrice = stockTicket.getCurrentPrice().add();
        stockTicket.setCurrentPrice(updatePrice);
        stockTicket.setLastUpdated(LocalDateTime.now());
        return stockRepository.save(stockTicket);

    }

    public void deleteStock(String ticker) {
        StockEntity stockTicket = stockRepository.findByTicket(ticker).orElseThrow(() -> new StockNotFoundException("Stock not found!"));
        stockRepository.delete(stockTicket);
    }

    public StockEntity getStockByTicker(String ticker) {
        return stockRepository.findByTicket(ticker).orElseThrow(() -> new StockNotFoundException(ticker));
    }
}
