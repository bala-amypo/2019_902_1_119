package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RiskThreshold;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.RiskThresholdRepository;
import com.example.demo.repository.UserPortfolioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskThresholdServiceImpl implements RiskThresholdService {
    
    private final RiskThresholdRepository riskThresholdRepository;
    
    public RiskThresholdServiceImpl(RiskThresholdRepository riskThresholdRepository) {
        this.riskThresholdRepository = riskThresholdRepository;
    }
    
    @Override
    public RiskThreshold createThreshold(RiskThreshold threshold) {
        validateThreshold(threshold);
        return riskThresholdRepository.save(threshold);
    }
    
    @Override
    public RiskThreshold updateThreshold(Long id, RiskThreshold threshold) {
        RiskThreshold existing = getThresholdById(id);
        validateThreshold(threshold);
        existing.setMaxSingleStockPercentage(threshold.getMaxSingleStockPercentage());
        existing.setMaxSectorPercentage(threshold.getMaxSectorPercentage());
        existing.setMaxOverallVolatility(threshold.getMaxOverallVolatility());
        return riskThresholdRepository.save(existing);
    }
    
    @Override
    public RiskThreshold getActiveThreshold() {
        return riskThresholdRepository.findByActiveTrue()
                .orElseThrow(() -> new ResourceNotFoundException("Active threshold not found"));
    }
    
    @Override
    public RiskThreshold getThresholdById(Long id) {
        return riskThresholdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Threshold not found"));
    }
    
    @Override
    public List<RiskThreshold> getAllThresholds() {
        return riskThresholdRepository.findAll();
    }
    
    @Override
    public RiskThreshold setThreshold(Long portfolioId, RiskThreshold threshold) {
        validateThreshold(threshold);
        return riskThresholdRepository.save(threshold);
    }
    
    @Override
    public RiskThreshold getThresholdForPortfolio(Long portfolioId) {
        return getActiveThreshold();
    }
    
    private void validateThreshold(RiskThreshold threshold) {
        if (threshold.getMaxSingleStockPercentage() != null && 
            (threshold.getMaxSingleStockPercentage() < 0 || threshold.getMaxSingleStockPercentage() > 100)) {
            throw new IllegalArgumentException("Max single stock percentage must be between 0 and 100");
        }
        if (threshold.getMaxSectorPercentage() != null && 
            (threshold.getMaxSectorPercentage() < 0 || threshold.getMaxSectorPercentage() > 100)) {
            throw new IllegalArgumentException("Max sector percentage must be between 0 and 100");
        }
    }
}
