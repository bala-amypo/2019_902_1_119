package com.example.demo.service;

import com.example.demo.model.PortfolioHolding;
import java.util.List;

public interface PortfolioHoldingService {
    PortfolioHolding addHolding(Long portfolioId, Long stockId, PortfolioHolding holding);
    List<PortfolioHolding> getHoldingsByPortfolio(Long portfolioId);
}