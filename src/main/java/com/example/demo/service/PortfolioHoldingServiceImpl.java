package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PortfolioHolding;
import com.example.demo.model.Stock;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.PortfolioHoldingRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.UserPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioHoldingServiceImpl implements PortfolioHoldingService {
    
    @Autowired
    private PortfolioHoldingRepository holdingRepository;
    
    @Autowired
    private UserPortfolioRepository portfolioRepository;
    
    @Autowired
    private StockRepository stockRepository;
    
    @Override
    public PortfolioHolding addHolding(Long portfolioId, Long stockId, PortfolioHolding holding) {
        UserPortfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        
        if (holding.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        if (holding.getMarketValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Market value must be non-negative");
        }
        
        holding.setPortfolio(portfolio);
        holding.setStock(stock);
        return holdingRepository.save(holding);
    }
    
    @Override
    public List<PortfolioHolding> getHoldingsByPortfolio(Long portfolioId) {
        return holdingRepository.findByPortfolioId(portfolioId);
    }
}