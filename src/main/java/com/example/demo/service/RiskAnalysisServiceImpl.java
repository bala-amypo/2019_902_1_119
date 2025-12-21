package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskAnalysisServiceImpl implements RiskAnalysisService {
    
    private final RiskAnalysisResultRepository riskAnalysisResultRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    private final PortfolioHoldingRepository portfolioHoldingRepository;
    private final RiskThresholdRepository riskThresholdRepository;
    
    public RiskAnalysisServiceImpl(RiskAnalysisResultRepository riskAnalysisResultRepository,
                                 UserPortfolioRepository userPortfolioRepository,
                                 PortfolioHoldingRepository portfolioHoldingRepository,
                                 RiskThresholdRepository riskThresholdRepository) {
        this.riskAnalysisResultRepository = riskAnalysisResultRepository;
        this.userPortfolioRepository = userPortfolioRepository;
        this.portfolioHoldingRepository = portfolioHoldingRepository;
        this.riskThresholdRepository = riskThresholdRepository;
    }
    
    @Override
    public RiskAnalysisResult analyzePortfolio(Long portfolioId) {
        UserPortfolio portfolio = userPortfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        
        List<PortfolioHolding> holdings = portfolioHoldingRepository.findByPortfolioId(portfolioId);
        
        if (holdings.isEmpty()) {
            RiskAnalysisResult result = new RiskAnalysisResult();
            result.setPortfolio(portfolio);
            result.setHighestStockPercentage(0.0);
            result.setHighestSectorPercentage(0.0);
            result.setIsHighRisk(false);
            return riskAnalysisResultRepository.save(result);
        }
        
        // Calculate total portfolio value
        BigDecimal totalValue = holdings.stream()
                .map(PortfolioHolding::getMarketValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calculate highest stock percentage
        double highestStockPercentage = holdings.stream()
                .mapToDouble(holding -> holding.getMarketValue().divide(totalValue, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100)
                .max()
                .orElse(0.0);
        
        // Calculate highest sector percentage
        Map<String, BigDecimal> sectorValues = new HashMap<>();
        for (PortfolioHolding holding : holdings) {
            String sector = holding.getStock().getSector();
            sectorValues.merge(sector, holding.getMarketValue(), BigDecimal::add);
        }
        
        double highestSectorPercentage = sectorValues.values().stream()
                .mapToDouble(value -> value.divide(totalValue, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100)
                .max()
                .orElse(0.0);
        
        // Check if high risk based on thresholds
        boolean isHighRisk = false;
        try {
            RiskThreshold threshold = riskThresholdRepository.findByActiveTrue().orElse(null);
            if (threshold != null) {
                if (threshold.getMaxSingleStockPercentage() != null && 
                    highestStockPercentage > threshold.getMaxSingleStockPercentage()) {
                    isHighRisk = true;
                }
                if (threshold.getMaxSectorPercentage() != null && 
                    highestSectorPercentage > threshold.getMaxSectorPercentage()) {
                    isHighRisk = true;
                }
            }
        } catch (Exception e) {
            // Continue without threshold check if no active threshold
        }
        
        RiskAnalysisResult result = new RiskAnalysisResult();
        result.setPortfolio(portfolio);
        result.setHighestStockPercentage(highestStockPercentage);
        result.setHighestSectorPercentage(highestSectorPercentage);
        result.setIsHighRisk(isHighRisk);
        
        return riskAnalysisResultRepository.save(result);
    }
    
    @Override
    public RiskAnalysisResult getAnalysisById(Long id) {
        return riskAnalysisResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found"));
    }
    
    @Override
    public List<RiskAnalysisResult> getAnalysesForPortfolio(Long portfolioId) {
        return riskAnalysisResultRepository.findByPortfolioId(portfolioId);
    }
}
