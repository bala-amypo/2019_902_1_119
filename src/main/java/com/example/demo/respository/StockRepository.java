
package com.example.demo.repository;

import com.example.demo.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByTicker(String ticker);
    List<Stock> findBySector(String sector);
}
