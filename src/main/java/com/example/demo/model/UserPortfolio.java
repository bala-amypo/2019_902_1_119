package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "user_portfolios")
public class UserPortfolio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String portfolioName;
    
    private LocalDateTime createdAt;
    
    private Timestamp updatedAt;
    
    private Boolean active = true;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioHolding> holdings;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<RiskAnalysisResult> analysisResults;
    
    public UserPortfolio() {}
    
    public UserPortfolio(User user, String portfolioName, LocalDateTime createdAt) {
        this.user = user;
        this.portfolioName = portfolioName;
        this.createdAt = createdAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Long getUserId() { return user != null ? user.getId() : null; }
    public void setUserId(Long userId) { 
        // This is a computed property, actual setting is done via setUser
    }
    
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public List<PortfolioHolding> getHoldings() { return holdings; }
    public void setHoldings(List<PortfolioHolding> holdings) { this.holdings = holdings; }
    
    public List<RiskAnalysisResult> getAnalysisResults() { return analysisResults; }
    public void setAnalysisResults(List<RiskAnalysisResult> analysisResults) { this.analysisResults = analysisResults; }
}
