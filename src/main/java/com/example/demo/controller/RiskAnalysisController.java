package com.example.demo.controller;

import com.example.demo.model.RiskAnalysisResult;
import com.example.demo.service.RiskAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/risk-analysis")
public class RiskAnalysisController {
    
    @Autowired
    private RiskAnalysisService analysisService;
    
    @PostMapping("/{portfolioId}")
    public ResponseEntity<RiskAnalysisResult> analyzePortfolio(@PathVariable Long portfolioId) {
        RiskAnalysisResult result = analysisService.analyzePortfolio(portfolioId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{portfolioId}")
    public ResponseEntity<List<RiskAnalysisResult>> getAnalyses(@PathVariable Long portfolioId) {
        List<RiskAnalysisResult> results = analysisService.getAnalysesForPortfolio(portfolioId);
        return ResponseEntity.ok(results);
    }
}