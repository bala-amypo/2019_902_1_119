package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "riskanalysisresults")
public class RiskAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private UserPortfolio portfolio;

    private Timestamp analysisDate;
    private Double highestStockPercentage;
    private Boolean isHighRisk;

    public RiskAnalysisResult() {}

    public RiskAnalysisResult(UserPortfolio portfolio, Timestamp analysisDate, Double highestStockPercentage, Boolean isHighRisk) {
        this.portfolio = portfolio;
        this.analysisDate = analysisDate;
        this.highestStockPercentage = highestStockPercentage;
        this.isHighRisk = isHighRisk;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserPortfolio getPortfolio() { return portfolio; }
    public void setPortfolio(UserPortfolio portfolio) { this.portfolio = portfolio; }
    public Timestamp getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(Timestamp analysisDate) { this.analysisDate = analysisDate; }
    public Double getHighestStockPercentage() { return highestStockPercentage; }
    public void setHighestStockPercentage(Double highestStockPercentage) { this.highestStockPercentage = highestStockPercentage; }
    public Boolean getIsHighRisk() { return isHighRisk; }
    public void setIsHighRisk(Boolean isHighRisk) { this.isHighRisk = isHighRisk; }
}