package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_analysis_results")
public class RiskAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "portfolio_id")
    private Long portfolioId;
    
    @Column(name = "analysis_date")
    private LocalDateTime analysisDate;
    
    @Column(name = "highest_stock_percentage")
    private Double highestStockPercentage;
    
    @Column(name = "highest_sector_percentage")
    private Double highestSectorPercentage;
    
    @Column(name = "is_high_risk")
    private Boolean isHighRisk;
    
    private String notes;
    
    public RiskAnalysisResult() {}
    
    public RiskAnalysisResult(Long portfolioId, LocalDateTime analysisDate, 
                            Double highestStockPercentage, Double highestSectorPercentage, 
                            Boolean isHighRisk, String notes) {
        this.portfolioId = portfolioId;
        this.analysisDate = analysisDate;
        this.highestStockPercentage = highestStockPercentage;
        this.highestSectorPercentage = highestSectorPercentage;
        this.isHighRisk = isHighRisk;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    
    public LocalDateTime getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(LocalDateTime analysisDate) { this.analysisDate = analysisDate; }
    
    public Double getHighestStockPercentage() { return highestStockPercentage; }
    public void setHighestStockPercentage(Double highestStockPercentage) { this.highestStockPercentage = highestStockPercentage; }
    
    public Double getHighestSectorPercentage() { return highestSectorPercentage; }
    public void setHighestSectorPercentage(Double highestSectorPercentage) { this.highestSectorPercentage = highestSectorPercentage; }
    
    public Boolean getIsHighRisk() { return isHighRisk; }
    public void setIsHighRisk(Boolean isHighRisk) { this.isHighRisk = isHighRisk; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}