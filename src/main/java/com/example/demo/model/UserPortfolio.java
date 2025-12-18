package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioHolding> holdings;

    @OneToMany(mappedBy = "portfolio")
    private List<RiskAnalysisResult> analyses;

    public UserPortfolio() {}

    public UserPortfolio(User user, String portfolioName, LocalDateTime createdAt) {
        this.user = user;
        this.portfolioName = portfolioName;
        this.createdAt = createdAt;
    }

    // getters & setters
}