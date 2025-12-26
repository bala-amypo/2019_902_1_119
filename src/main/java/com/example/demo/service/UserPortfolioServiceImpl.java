package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.UserPortfolioRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserPortfolioServiceImpl implements UserPortfolioService {
    
    @Autowired
    private UserPortfolioRepository portfolioRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserPortfolio createPortfolio(UserPortfolio portfolio) {
        if (portfolio.getUser() != null && portfolio.getUser().getId() != null) {
            userRepository.findById(portfolio.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }
        portfolio.setCreatedAt(LocalDateTime.now());
        return portfolioRepository.save(portfolio);
    }
    
    @Override
    public UserPortfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
    
    @Override
    public List<UserPortfolio> getPortfoliosByUser(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }
}