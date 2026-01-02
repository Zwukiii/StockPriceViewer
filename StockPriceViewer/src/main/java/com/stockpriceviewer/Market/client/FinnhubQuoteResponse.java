package com.stockpriceviewer.Market.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinnhubQuoteResponse {
    @JsonProperty("c")
    private BigDecimal currentPrice;
    @JsonProperty("h")
    private BigDecimal high;
    @JsonProperty("l")
    private BigDecimal low;
    @JsonProperty("o")
    private BigDecimal open;
    @JsonProperty("pc")
    private BigDecimal previousClose;
}
