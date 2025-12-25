package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_thresholds")
public class RiskThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "threshold_name", unique = true, nullable = false)
    private String thresholdName;
    
    @Column(name = "max_single_stock_percentage", nullable = false)
    private Double maxSingleStockPercentage;
    
    @Column(name = "max_sector_percentage", nullable = false)
    private Double maxSectorPercentage;
    
    @Column(name = "is_active")
    private Boolean isActive = false;
    
    public RiskThreshold() {}
    
    public RiskThreshold(String thresholdName, Double maxSingleStockPercentage, Double maxSectorPercentage) {
        this.thresholdName = thresholdName;
        this.maxSingleStockPercentage = maxSingleStockPercentage;
        this.maxSectorPercentage = maxSectorPercentage;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getThresholdName() { return thresholdName; }
    public void setThresholdName(String thresholdName) { this.thresholdName = thresholdName; }
    
    public Double getMaxSingleStockPercentage() { return maxSingleStockPercentage; }
    public void setMaxSingleStockPercentage(Double maxSingleStockPercentage) { 
        this.maxSingleStockPercentage = maxSingleStockPercentage; 
    }
    
    public Double getMaxSectorPercentage() { return maxSectorPercentage; }
    public void setMaxSectorPercentage(Double maxSectorPercentage) { 
        this.maxSectorPercentage = maxSectorPercentage; 
    }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}