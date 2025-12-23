package com.example.demo.controller;

import com.example.demo.model.RiskAnalysisResult;
import com.example.demo.service.RiskAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/risk-analysis")
@Tag(name = "Risk Analysis", description = "Risk analysis endpoints")
public class RiskAnalysisController {
    
    private final RiskAnalysisService riskAnalysisService;
    
    public RiskAnalysisController(RiskAnalysisService riskAnalysisService) {
        this.riskAnalysisService = riskAnalysisService;
    }
    
    @PostMapping("/analyze/{portfolioId}")
    @Operation(summary = "Run risk analysis for portfolio")
    public ResponseEntity<RiskAnalysisResult> analyzePortfolio(@PathVariable Long portfolioId) {
        RiskAnalysisResult result = riskAnalysisService.analyzePortfolio(portfolioId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get analysis result by ID")
    public ResponseEntity<RiskAnalysisResult> getAnalysis(@PathVariable Long id) {
        RiskAnalysisResult result = riskAnalysisService.getAnalysisById(id);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/portfolio/{portfolioId}")
    @Operation(summary = "Get all analyses for portfolio")
    public ResponseEntity<List<RiskAnalysisResult>> getAnalysesForPortfolio(@PathVariable Long portfolioId) {
        List<RiskAnalysisResult> results = riskAnalysisService.getAnalysesForPortfolio(portfolioId);
        return ResponseEntity.ok(results);
    }
}