package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_thresholds")
public class RiskThreshold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserPortfolio portfolio;

    private Double maxSingleStockPercentage;
    private Double maxOverallVolatility;

    public RiskThreshold() {}

    public RiskThreshold(UserPortfolio portfolio,
                         Double maxSingleStockPercentage,
                         Double maxOverallVolatility) {
        this.portfolio = portfolio;
        this.maxSingleStockPercentage = maxSingleStockPercentage;
        this.maxOverallVolatility = maxOverallVolatility;
    }

    // getters & setters
}