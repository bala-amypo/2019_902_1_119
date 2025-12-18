
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_analysis_results")
public class RiskAnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserPortfolio portfolio;

    private LocalDateTime analysisDate;
    private Double highestStockPercentage;
    private Boolean isHighRisk;

    public RiskAnalysisResult() {}

    public RiskAnalysisResult(UserPortfolio portfolio,
                              LocalDateTime analysisDate,
                              Double highestStockPercentage,
                              Boolean isHighRisk) {
        this.portfolio = portfolio;
        this.analysisDate = analysisDate;
        this.highestStockPercentage = highestStockPercentage;
        this.isHighRisk = isHighRisk;
    }

    @PrePersist
    public void onCreate() {
        this.analysisDate = LocalDateTime.now();
    }

    // getters & setters
}