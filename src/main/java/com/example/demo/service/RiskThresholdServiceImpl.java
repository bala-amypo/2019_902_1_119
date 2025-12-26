package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RiskThreshold;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.RiskThresholdRepository;
import com.example.demo.repository.UserPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskThresholdServiceImpl implements RiskThresholdService {
    
    @Autowired
    private RiskThresholdRepository thresholdRepository;
    
    @Autowired
    private UserPortfolioRepository portfolioRepository;
    
    @Override
    public RiskThreshold setThreshold(Long portfolioId, RiskThreshold threshold) {
        UserPortfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        
        if (threshold.getMaxSingleStockPercentage() < 0 || threshold.getMaxSingleStockPercentage() > 100) {
            throw new IllegalArgumentException("Max single stock percentage must be between 0 and 100");
        }
        
        threshold.setPortfolio(portfolio);
        return thresholdRepository.save(threshold);
    }
    
    @Override
    public RiskThreshold getThresholdForPortfolio(Long portfolioId) {
        return thresholdRepository.findByPortfolioId(portfolioId)
                .orElse(null);
    }
}