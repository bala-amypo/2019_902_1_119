package com.example.demo.controller;

import com.example.demo.model.UserPortfolio;
import com.example.demo.service.UserPortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Portfolios", description = "User portfolio management")
@RestController
@RequestMapping("/api/portfolios")
@SecurityRequirement(name = "bearerAuth")
public class UserPortfolioController {
    
    private final UserPortfolioService userPortfolioService;

    public UserPortfolioController(UserPortfolioService userPortfolioService) {
        this.userPortfolioService = userPortfolioService;
    }

    @Operation(summary = "Create portfolio")
    @PostMapping
    public ResponseEntity<UserPortfolio> createPortfolio(@RequestBody UserPortfolio portfolio) {
        UserPortfolio created = userPortfolioService.createPortfolio(portfolio);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Update portfolio")
    @PutMapping("/{id}")
    public ResponseEntity<UserPortfolio> updatePortfolio(@PathVariable Long id, @RequestBody UserPortfolio portfolio) {
        UserPortfolio updated = userPortfolioService.updatePortfolio(id, portfolio);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get portfolio by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserPortfolio> getPortfolioById(@PathVariable Long id) {
        UserPortfolio portfolio = userPortfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }

    @Operation(summary = "Get portfolios by user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPortfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        List<UserPortfolio> portfolios = userPortfolioService.getPortfoliosByUser(userId);
        return ResponseEntity.ok(portfolios);
    }

    @Operation(summary = "Deactivate portfolio")
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePortfolio(@PathVariable Long id) {
        userPortfolioService.deactivatePortfolio(id);
        return ResponseEntity.ok().build();
    }
}