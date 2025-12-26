package com.example.demo.controller;

import com.example.demo.model.PortfolioHolding;
import com.example.demo.service.PortfolioHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class PortfolioHoldingController {
    
    @Autowired
    private PortfolioHoldingService holdingService;
    
    @PostMapping("/{portfolioId}/{stockId}")
    public ResponseEntity<PortfolioHolding> addHolding(@PathVariable Long portfolioId, 
                                                      @PathVariable Long stockId,
                                                      @RequestBody PortfolioHolding holding) {
        PortfolioHolding created = holdingService.addHolding(portfolioId, stockId, holding);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<PortfolioHolding>> getHoldingsByPortfolio(@PathVariable Long portfolioId) {
        List<PortfolioHolding> holdings = holdingService.getHoldingsByPortfolio(portfolioId);
        return ResponseEntity.ok(holdings);
    }
}