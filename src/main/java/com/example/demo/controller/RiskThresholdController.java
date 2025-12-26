
package com.example.demo.controller;

import com.example.demo.model.RiskThreshold;
import com.example.demo.service.RiskThresholdService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-thresholds")
@Tag(name = "Risk Thresholds")
public class RiskThresholdController {
    
    private final RiskThresholdService thresholdService;

    public RiskThresholdController(RiskThresholdService thresholdService) {
        this.thresholdService = thresholdService;
    }

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