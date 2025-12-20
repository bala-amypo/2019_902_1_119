package com.example.demo.service;

import com.example.demo.model.Stock;
import java.util.List;

public interface StockService {

    Stock createStock(Stock stock);

    Stock updateStock(Long id, Stock stock);

    Stock getStockById(Long id);

    List<Stock> getAllStocks();

    void deactivateStock(Long id);
}
