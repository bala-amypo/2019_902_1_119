package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PortfolioHolding;
import com.example.demo.model.Stock;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.PortfolioHoldingRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.UserPortfolioRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioHoldingServiceImpl implements PortfolioHoldingService {
    
    private final PortfolioHoldingRepository portfolioHoldingRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    private final StockRepository stockRepository;
    
    public PortfolioHoldingServiceImpl(PortfolioHoldingRepository portfolioHoldingRepository, 
                                     UserPortfolioRepository userPortfolioRepository,
                                     StockRepository stockRepository) {
        this.portfolioHoldingRepository = portfolioHoldingRepository;
        this.userPortfolioRepository = userPortfolioRepository;
        this.stockRepository = stockRepository;
    }
    
    @Override
    public PortfolioHolding createHolding(PortfolioHolding holding) {
        validateHolding(holding);
        return portfolioHoldingRepository.save(holding);
    }
    
    @Override
    public PortfolioHolding updateHolding(Long id, PortfolioHolding holding) {
        PortfolioHolding existing = getHoldingById(id);
        validateHolding(holding);
        existing.setQuantity(holding.getQuantity());
        existing.setMarketValue(holding.getMarketValue());
        return portfolioHoldingRepository.save(existing);
    }
    
    @Override
    public PortfolioHolding getHoldingById(Long id) {
        return portfolioHoldingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holding not found"));
    }
    
    @Override
    public List<PortfolioHolding> getHoldingsByPortfolio(Long portfolioId) {
        return portfolioHoldingRepository.findByPortfolioId(portfolioId);
    }
    
    @Override
    public void deleteHolding(Long id) {
        PortfolioHolding holding = getHoldingById(id);
        portfolioHoldingRepository.delete(holding);
    }
    
    @Override
    public PortfolioHolding addHolding(Long portfolioId, Long stockId, PortfolioHolding holding) {
        UserPortfolio portfolio = userPortfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        
        holding.setPortfolio(portfolio);
        holding.setStock(stock);
        validateHolding(holding);
        
        return portfolioHoldingRepository.save(holding);
    }
    
    private void validateHolding(PortfolioHolding holding) {
        if (holding.getQuantity() == null || holding.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        if (holding.getMarketValue() == null || holding.getMarketValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Market value must be >= 0");
        }
    }
}