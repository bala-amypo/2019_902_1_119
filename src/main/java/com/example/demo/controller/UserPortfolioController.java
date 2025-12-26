
package com.example.demo.controller;

import com.example.demo.model.UserPortfolio;
import com.example.demo.service.UserPortfolioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@Tag(name = "Portfolios")
public class UserPortfolioController {
    
    private final UserPortfolioService portfolioService;

    public UserPortfolioController(UserPortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public ResponseEntity<UserPortfolio> createPortfolio(@RequestBody UserPortfolio portfolio) {
        UserPortfolio created = portfolioService.createPortfolio(portfolio);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPortfolio> getPortfolio(@PathVariable Long id) {
        UserPortfolio portfolio = portfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPortfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        List<UserPortfolio> portfolios = portfolioService.getPortfoliosByUser(userId);
        return ResponseEntity.ok(portfolios);
    }
}