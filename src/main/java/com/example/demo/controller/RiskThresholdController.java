package com.example.demo.controller;

import com.example.demo.model.RiskThreshold;
import com.example.demo.service.RiskThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-thresholds")
public class RiskThresholdController {
    
    @Autowired
    private RiskThresholdService thresholdService;
    
    @PostMapping("/{portfolioId}")
    public ResponseEntity<RiskThreshold> setThreshold(@PathVariable Long portfolioId, 
                                                     @RequestBody RiskThreshold threshold) {
        RiskThreshold created = thresholdService.setThreshold(portfolioId, threshold);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/{portfolioId}")
    public ResponseEntity<RiskThreshold> getThreshold(@PathVariable Long portfolioId) {
        RiskThreshold threshold = thresholdService.getThresholdForPortfolio(portfolioId);
        return ResponseEntity.ok(threshold);
    }
}