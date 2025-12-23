package com.example.demo.controller;

import com.example.demo.model.RiskThreshold;
import com.example.demo.service.RiskThresholdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/risk-thresholds")
@Tag(name = "Risk Thresholds", description = "Risk threshold management endpoints")
public class RiskThresholdController {
    
    private final RiskThresholdService riskThresholdService;
    
    public RiskThresholdController(RiskThresholdService riskThresholdService) {
        this.riskThresholdService = riskThresholdService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new risk threshold")
    public ResponseEntity<RiskThreshold> createThreshold(@RequestBody RiskThreshold threshold) {
        RiskThreshold createdThreshold = riskThresholdService.createThreshold(threshold);
        return ResponseEntity.ok(createdThreshold);
    }
    
    @PostMapping("/{portfolioId}")
    @Operation(summary = "Set threshold for portfolio")
    public ResponseEntity<RiskThreshold> setThreshold(@PathVariable Long portfolioId, @RequestBody RiskThreshold threshold) {
        RiskThreshold createdThreshold = riskThresholdService.setThreshold(portfolioId, threshold);
        return ResponseEntity.ok(createdThreshold);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing risk threshold")
    public ResponseEntity<RiskThreshold> updateThreshold(@PathVariable Long id, @RequestBody RiskThreshold threshold) {
        RiskThreshold updatedThreshold = riskThresholdService.updateThreshold(id, threshold);
        return ResponseEntity.ok(updatedThreshold);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get active risk threshold")
    public ResponseEntity<RiskThreshold> getActiveThreshold() {
        RiskThreshold threshold = riskThresholdService.getActiveThreshold();
        return ResponseEntity.ok(threshold);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get risk threshold by ID")
    public ResponseEntity<RiskThreshold> getThreshold(@PathVariable Long id) {
        RiskThreshold threshold = riskThresholdService.getThresholdById(id);
        return ResponseEntity.ok(threshold);
    }
    
    @GetMapping("/{portfolioId}")
    @Operation(summary = "Get threshold for portfolio")
    public ResponseEntity<RiskThreshold> getThresholdForPortfolio(@PathVariable Long portfolioId) {
        RiskThreshold threshold = riskThresholdService.getThresholdForPortfolio(portfolioId);
        return ResponseEntity.ok(threshold);
    }
    
    @GetMapping
    @Operation(summary = "Get all risk thresholds")
    public ResponseEntity<List<RiskThreshold>> getAllThresholds() {
        List<RiskThreshold> thresholds = riskThresholdService.getAllThresholds();
        return ResponseEntity.ok(thresholds);
    }
}