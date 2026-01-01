package portfolio;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class StockResponseDTO {
    private String ticket;
    private String companyName;
    private BigDecimal currentPrice;
    private LocalDateTime lastUpdated;


    public StockResponseDTO(String ticket, String companyName, BigDecimal currentPrice, LocalDateTime lastUpdated) {
        this.ticket = ticket;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.lastUpdated = lastUpdated;
    }
}
