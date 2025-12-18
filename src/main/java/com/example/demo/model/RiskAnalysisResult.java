
 
 package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class RiskAnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserPortfolio portfolio;

    private Timestamp analysisDate = new Timestamp(System.currentTimeMillis());
    private Double highestStockPercentage;
    private Double highestSectorPercentage;
    private Boolean isHighRisk;
    private String notes;

    // getters & setters
}