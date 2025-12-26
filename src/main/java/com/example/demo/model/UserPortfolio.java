package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_portfolios")
public class UserPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "portfolio_name", nullable = false)
    private String portfolioName;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioHolding> holdings;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<RiskAnalysisResult> riskAnalyses;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<RiskThreshold> riskThresholds;
    
    public UserPortfolio() {}
    
    public UserPortfolio(User user, String portfolioName, LocalDateTime createdAt) {
        this.user = user;
        this.portfolioName = portfolioName;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public List<PortfolioHolding> getHoldings() { return holdings; }
    public void setHoldings(List<PortfolioHolding> holdings) { this.holdings = holdings; }
    
    public List<RiskAnalysisResult> getRiskAnalyses() { return riskAnalyses; }
    public void setRiskAnalyses(List<RiskAnalysisResult> riskAnalyses) { this.riskAnalyses = riskAnalyses; }
    
    public List<RiskThreshold> getRiskThresholds() { return riskThresholds; }
    public void setRiskThresholds(List<RiskThreshold> riskThresholds) { this.riskThresholds = riskThresholds; }
}