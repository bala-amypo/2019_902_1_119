package com.example.demo.controller;

import com.example.demo.model.UserPortfolio;
import com.example.demo.service.UserPortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@Tag(name = "Portfolios", description = "Portfolio management endpoints")
public class UserPortfolioController {
    
    private final UserPortfolioService userPortfolioService;
    
    public UserPortfolioController(UserPortfolioService userPortfolioService) {
        this.userPortfolioService = userPortfolioService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new portfolio")
    public ResponseEntity<UserPortfolio> createPortfolio(@RequestBody UserPortfolio portfolio) {
        UserPortfolio createdPortfolio = userPortfolioService.createPortfolio(portfolio);
        return ResponseEntity.ok(createdPortfolio);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing portfolio")
    public ResponseEntity<UserPortfolio> updatePortfolio(@PathVariable Long id, @RequestBody UserPortfolio portfolio) {
        UserPortfolio updatedPortfolio = userPortfolioService.updatePortfolio(id, portfolio);
        return ResponseEntity.ok(updatedPortfolio);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get portfolio by ID")
    public ResponseEntity<UserPortfolio> getPortfolio(@PathVariable Long id) {
        UserPortfolio portfolio = userPortfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get portfolios for a user")
    public ResponseEntity<List<UserPortfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        List<UserPortfolio> portfolios = userPortfolioService.getPortfoliosByUser(userId);
        return ResponseEntity.ok(portfolios);
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a portfolio")
    public ResponseEntity<String> deactivatePortfolio(@PathVariable Long id) {
        userPortfolioService.deactivatePortfolio(id);
        return ResponseEntity.ok("Portfolio deactivated successfully");
    }
}