package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "riskthresholds")
public class RiskThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private UserPortfolio portfolio;

    private Double maxSingleStockPercentage;
    private Double maxOverallVolatility;

    public RiskThreshold() {}

    public RiskThreshold(UserPortfolio portfolio, Double maxSingleStockPercentage, Double maxOverallVolatility) {
        this.portfolio = portfolio;
        this.maxSingleStockPercentage = maxSingleStockPercentage;
        this.maxOverallVolatility = maxOverallVolatility;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserPortfolio getPortfolio() { return portfolio; }
    public void setPortfolio(UserPortfolio portfolio) { this.portfolio = portfolio; }
    public Double getMaxSingleStockPercentage() { return maxSingleStockPercentage; }
    public void setMaxSingleStockPercentage(Double maxSingleStockPercentage) { this.maxSingleStockPercentage = maxSingleStockPercentage; }
    public Double getMaxOverallVolatility() { return maxOverallVolatility; }
    public void setMaxOverallVolatility(Double maxOverallVolatility) { this.maxOverallVolatility = maxOverallVolatility; }
}
