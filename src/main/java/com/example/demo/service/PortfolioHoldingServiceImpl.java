
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PortfolioHolding;
import com.example.demo.repository.PortfolioHoldingRepository;
import com.example.demo.repository.UserPortfolioRepository;
import com.example.demo.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        if (holding.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        if (holding.getMarketValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Market value must be >= 0");
        }
        userPortfolioRepository.findById(holding.getPortfolioId())
            .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        stockRepository.findById(holding.getStockId())
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        holding.setLastUpdated(LocalDateTime.now());
        return portfolioHoldingRepository.save(holding);
    }

    @Override
    public PortfolioHolding updateHolding(Long id, PortfolioHolding holding) {
        PortfolioHolding existing = getHoldingById(id);
        holding.setId(id);
        holding.setLastUpdated(LocalDateTime.now());
        return portfolioHoldingRepository.save(holding);
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
        if (!portfolioHoldingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Holding not found");
        }
        portfolioHoldingRepository.deleteById(id);
    }
}