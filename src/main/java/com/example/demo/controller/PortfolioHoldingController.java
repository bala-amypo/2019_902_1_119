
package com.example.demo.controller;

import com.example.demo.model.PortfolioHolding;
import com.example.demo.service.PortfolioHoldingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
@Tag(name = "Holdings")
public class PortfolioHoldingController {
    
    private final PortfolioHoldingService holdingService;

    public PortfolioHoldingController(PortfolioHoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @PostMapping("/{portfolioId}/stock/{stockId}")
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