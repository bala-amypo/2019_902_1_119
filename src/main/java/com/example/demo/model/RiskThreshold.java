package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_thresholds")
public class RiskThreshold {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private UserPortfolio portfolio;
    
    private String thresholdName;
    
    private Double maxSingleStockPercentage;
    
    private Double maxSectorPercentage;
    
    private Double maxOverallVolatility;
    
    private Boolean active = true;
    
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
    
    public String getThresholdName() { return thresholdName; }
    public void setThresholdName(String thresholdName) { this.thresholdName = thresholdName; }
    
    public Double getMaxSingleStockPercentage() { return maxSingleStockPercentage; }
    public void setMaxSingleStockPercentage(Double maxSingleStockPercentage) { this.maxSingleStockPercentage = maxSingleStockPercentage; }
    
    public Double getMaxSectorPercentage() { return maxSectorPercentage; }
    public void setMaxSectorPercentage(Double maxSectorPercentage) { this.maxSectorPercentage = maxSectorPercentage; }
    
    public Double getMaxOverallVolatility() { return maxOverallVolatility; }
    public void setMaxOverallVolatility(Double maxOverallVolatility) { this.maxOverallVolatility = maxOverallVolatility; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
