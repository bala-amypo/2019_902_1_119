package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_analysis_results")
public class RiskAnalysisResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private UserPortfolio portfolio;
    
    private Timestamp analysisDate;
    
    private LocalDateTime analysisDateTime;
    
    private Double highestStockPercentage;
    
    private Double highestSectorPercentage;
    
    private Boolean isHighRisk;
    
    private String notes;
    
    public RiskAnalysisResult() {}
    
    public RiskAnalysisResult(UserPortfolio portfolio, Timestamp analysisDate, Double highestStockPercentage, Boolean isHighRisk) {
        this.portfolio = portfolio;
        this.analysisDate = analysisDate;
        this.highestStockPercentage = highestStockPercentage;
        this.isHighRisk = isHighRisk;
    }
    
    @PrePersist
    protected void onCreate() {
        analysisDate = new Timestamp(System.currentTimeMillis());
        analysisDateTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public UserPortfolio getPortfolio() { return portfolio; }
    public void setPortfolio(UserPortfolio portfolio) { this.portfolio = portfolio; }
    
    public Timestamp getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(Timestamp analysisDate) { this.analysisDate = analysisDate; }
    
    public LocalDateTime getAnalysisDateTime() { return analysisDateTime; }
    public void setAnalysisDateTime(LocalDateTime analysisDateTime) { this.analysisDateTime = analysisDateTime; }
    
    public Double getHighestStockPercentage() { return highestStockPercentage; }
    public void setHighestStockPercentage(Double highestStockPercentage) { this.highestStockPercentage = highestStockPercentage; }
    
    public Double getHighestSectorPercentage() { return highestSectorPercentage; }
    public void setHighestSectorPercentage(Double highestSectorPercentage) { this.highestSectorPercentage = highestSectorPercentage; }
    
    public Boolean getIsHighRisk() { return isHighRisk; }
    public void setIsHighRisk(Boolean isHighRisk) { this.isHighRisk = isHighRisk; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}