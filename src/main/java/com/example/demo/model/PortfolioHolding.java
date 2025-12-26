package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "portfolioholdings")
public class PortfolioHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private UserPortfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private Double quantity;
    private BigDecimal marketValue;

    public PortfolioHolding() {}

    public PortfolioHolding(UserPortfolio portfolio, Stock stock, Double quantity, BigDecimal marketValue) {
        this.portfolio = portfolio;
        this.stock = stock;
        this.quantity = quantity;
        this.marketValue = marketValue;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserPortfolio getPortfolio() { return portfolio; }
    public void setPortfolio(UserPortfolio portfolio) { this.portfolio = portfolio; }
    public Stock getStock() { return stock; }
    public void setStock(Stock stock) { this.stock = stock; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public BigDecimal getMarketValue() { return marketValue; }
    public void setMarketValue(BigDecimal marketValue) { this.marketValue = marketValue; }
}
