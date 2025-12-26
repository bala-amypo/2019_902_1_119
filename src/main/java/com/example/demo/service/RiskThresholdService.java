package com.example.demo.service;

import com.example.demo.model.RiskThreshold;

public interface RiskThresholdService {
    RiskThreshold setThreshold(Long portfolioId, RiskThreshold threshold);
    RiskThreshold getThresholdForPortfolio(Long portfolioId);
}