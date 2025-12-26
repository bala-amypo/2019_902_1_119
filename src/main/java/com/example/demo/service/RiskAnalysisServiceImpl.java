package com.example.demo.service;

import com.example.demo.model.PortfolioHolding;
import com.example.demo.model.RiskAnalysisResult;
import com.example.demo.model.RiskThreshold;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.RiskAnalysisResultRepository;
import com.example.demo.repository.UserPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

@Service
public class RiskAnalysisServiceImpl implements RiskAnalysisService {
    
    @Autowired
    private RiskAnalysisResultRepository analysisRepository;
    
    @Autowired
    private UserPortfolioRepository portfolioRepository;
    
    @Autowired
    private PortfolioHoldingService holdingService;
    
    @Autowired
    private RiskThresholdService thresholdService;
    
    @Override
    public RiskAnalysisResult analyzePortfolio(Long portfolioId) {
        UserPortfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        
        List<PortfolioHolding> holdings = holdingService.getHoldingsByPortfolio(portfolioId);
        
        BigDecimal totalValue = holdings.stream()
                .map(PortfolioHolding::getMarketValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        double highestPercentage = 0.0;
        if (totalValue.compareTo(BigDecimal.ZERO) > 0) {
            highestPercentage = holdings.stream()
                    .mapToDouble(h -> h.getMarketValue().divide(totalValue, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue())
                    .max()
                    .orElse(0.0);
        }
        
        RiskThreshold threshold = thresholdService.getThresholdForPortfolio(portfolioId);
        boolean isHighRisk = threshold != null && highestPercentage > threshold.getMaxSingleStockPercentage();
        
        RiskAnalysisResult result = new RiskAnalysisResult();
        result.setPortfolio(portfolio);
        result.setAnalysisDate(new Timestamp(System.currentTimeMillis()));
        result.setHighestStockPercentage(highestPercentage);
        result.setHighRisk(isHighRisk);
        
        return analysisRepository.save(result);
    }
    
    @Override
    public List<RiskAnalysisResult> getAnalysesForPortfolio(Long portfolioId) {
        return analysisRepository.findByPortfolioId(portfolioId);
    }
}