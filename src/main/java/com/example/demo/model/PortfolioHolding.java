package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "portfolio_holdings")
public class PortfolioHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserPortfolio portfolio;

    @ManyToOne
    private Stock stock;

    private Double quantity;
    private BigDecimal marketValue;

    public PortfolioHolding() {}

    public PortfolioHolding(UserPortfolio portfolio, Stock stock,
                            Double quantity, BigDecimal marketValue) {
        this.portfolio = portfolio;
        this.stock = stock;
        this.quantity = quantity;
        this.marketValue = marketValue;
    }

    // getters & setters
}