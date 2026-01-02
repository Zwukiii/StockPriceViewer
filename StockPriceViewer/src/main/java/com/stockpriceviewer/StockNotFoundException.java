package com.stockpriceviewer;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String ticker) {
        super("Stock not found: " + ticker);
    }
}
