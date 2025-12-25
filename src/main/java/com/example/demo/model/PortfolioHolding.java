package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "portfolio_holdings")
public class PortfolioHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "portfolio_id")
    private Long portfolioId;
    
    @Column(name = "stock_id")
    private Long stockId;
    
    @Column(nullable = false)
    private Double quantity;
    
    @Column(name = "market_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal marketValue;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    public PortfolioHolding() {}
    
    public PortfolioHolding(Long portfolioId, Long stockId, Double quantity, BigDecimal marketValue) {
        this.portfolioId = portfolioId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.marketValue = marketValue;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    
    public Long getStockId() { return stockId; }
    public void setStockId(Long stockId) { this.stockId = stockId; }
    
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    
    public BigDecimal getMarketValue() { return marketValue; }
    public void setMarketValue(BigDecimal marketValue) { this.marketValue = marketValue; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}