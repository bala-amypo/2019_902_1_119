
 package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class UserPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;
    private String portfolioName;
    private Boolean active = true;
    private Timestamp createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = Timestamp.from(Instant.now());
    }

    // getters & setters
}