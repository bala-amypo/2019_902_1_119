package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ticker;

    private String companyName;
    private String sector;
    private Boolean isActive;

    @OneToMany(mappedBy = "stock")
    private List<PortfolioHolding> holdings;

    public Stock() {}

    public Stock(String ticker, String companyName, String sector, Boolean isActive) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.sector = sector;
        this.isActive = isActive;
    }

    // getters & setters
}
