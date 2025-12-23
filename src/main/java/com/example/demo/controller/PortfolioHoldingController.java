package com.example.demo.controller;

import com.example.demo.model.PortfolioHolding;
import com.example.demo.service.PortfolioHoldingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/holdings")
@Tag(name = "Holdings", description = "Portfolio holding management endpoints")
public class PortfolioHoldingController {
    
    private final PortfolioHoldingService portfolioHoldingService;
    
    public PortfolioHoldingController(PortfolioHoldingService portfolioHoldingService) {
        this.portfolioHoldingService = portfolioHoldingService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new holding")
    public ResponseEntity<PortfolioHolding> createHolding(@RequestBody PortfolioHolding holding) {
        PortfolioHolding createdHolding = portfolioHoldingService.createHolding(holding);
        return ResponseEntity.ok(createdHolding);
    }
    
    @PostMapping("/{portfolioId}/{stockId}")
    @Operation(summary = "Add holding to portfolio")
    public ResponseEntity<PortfolioHolding> addHolding(@PathVariable Long portfolioId, 
                                                       @PathVariable Long stockId,
                                                       @RequestBody PortfolioHolding holding) {
        PortfolioHolding createdHolding = portfolioHoldingService.addHolding(portfolioId, stockId, holding);
        return ResponseEntity.ok(createdHolding);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing holding")
    public ResponseEntity<PortfolioHolding> updateHolding(@PathVariable Long id, @RequestBody PortfolioHolding holding) {
        PortfolioHolding updatedHolding = portfolioHoldingService.updateHolding(id, holding);
        return ResponseEntity.ok(updatedHolding);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get holding by ID")
    public ResponseEntity<PortfolioHolding> getHolding(@PathVariable Long id) {
        PortfolioHolding holding = portfolioHoldingService.getHoldingById(id);
        return ResponseEntity.ok(holding);
    }
    
    @GetMapping("/portfolio/{portfolioId}")
    @Operation(summary = "Get holdings by portfolio")
    public ResponseEntity<List<PortfolioHolding>> getHoldingsByPortfolio(@PathVariable Long portfolioId) {
        List<PortfolioHolding> holdings = portfolioHoldingService.getHoldingsByPortfolio(portfolioId);
        return ResponseEntity.ok(holdings);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a holding")
    public ResponseEntity<String> deleteHolding(@PathVariable Long id) {
        portfolioHoldingService.deleteHolding(id);
        return ResponseEntity.ok("Holding deleted successfully");
    }
}
