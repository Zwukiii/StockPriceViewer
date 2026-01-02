package com.stockpriceviewer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findByTicker(String ticker);

}
