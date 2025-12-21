package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    
    private final StockRepository stockRepository;
    
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
    @Override
    public Stock createStock(Stock stock) {
        if (stockRepository.findByTicker(stock.getTicker()).isPresent()) {
            throw new IllegalArgumentException("Duplicate ticker");
        }
        return stockRepository.save(stock);
    }
    
    @Override
    public Stock updateStock(Long id, Stock stock) {
        Stock existing = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        
        existing.setCompanyName(stock.getCompanyName());
        existing.setSector(stock.getSector());
        existing.setIsActive(stock.getIsActive());
        
        return stockRepository.save(existing);
    }
    
    @Override
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
    }
    
    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
    
    @Override
    public void deactivateStock(Long id) {
        Stock stock = getStockById(id);
        stock.setIsActive(false);
        stockRepository.save(stock);
    }
}