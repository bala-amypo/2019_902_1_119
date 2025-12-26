
package com.example.demo.service;

import com.example.demo.model.PortfolioHolding;
import com.example.demo.model.RiskAnalysisResult;
import com.example.demo.model.RiskThreshold;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.PortfolioHoldingRepository;
import com.example.demo.repository.RiskAnalysisResultRepository;
import com.example.demo.repository.RiskThresholdRepository;
import com.example.demo.repository.UserPortfolioRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class RiskAnalysisServiceImpl implements RiskAnalysisService {
    
    private final RiskAnalysisResultRepository analysisRepository;
    private final UserPortfolioRepository portfolioRepository;
    private final PortfolioHoldingRepository holdingRepository;
    private final RiskThresholdRepository thresholdRepository;

    public RiskAnalysisServiceImpl(RiskAnalysisResultRepository analysisRepository,
                                 UserPortfolioRepository portfolioRepository,
                                 PortfolioHoldingRepository holdingRepository,
                                 RiskThresholdRepository thresholdRepository) {
        this.analysisRepository = analysisRepository;
        this.portfolioRepository = portfolioRepository;
        this.holdingRepository = holdingRepository;
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public RiskAnalysisResult analyzePortfolio(Long portfolioId) {
        UserPortfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        
        List<PortfolioHolding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        RiskThreshold threshold = thresholdRepository.findById(portfolioId)
            .orElseThrow(() -> new RuntimeException("Threshold not found"));

        BigDecimal totalValue = holdings.stream()
            .map(PortfolioHolding::getMarketValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        double highestPercentage = 0.0;
        for (PortfolioHolding holding : holdings) {
            double percentage = holding.getMarketValue()
                .divide(totalValue, 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
            if (percentage > highestPercentage) {
                highestPercentage = percentage;
            }
        }

        RiskAnalysisResult result = new RiskAnalysisResult();
        result.setPortfolio(portfolio);
        result.setAnalysisDate(new Timestamp(System.currentTimeMillis()));
        result.setHighestStockPercentage(highestPercentage);
        result.setIsHighRisk(highestPercentage > threshold.getMaxSingleStockPercentage());

        return analysisRepository.save(result);
    }

    @Override
    public List<RiskAnalysisResult> getAnalysesForPortfolio(Long portfolioId) {
        return analysisRepository.findByPortfolioId(portfolioId);
    }
}