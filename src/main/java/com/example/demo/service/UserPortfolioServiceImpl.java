package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.UserPortfolioRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserPortfolioServiceImpl implements UserPortfolioService {
    
    private final UserPortfolioRepository userPortfolioRepository;
    private final UserRepository userRepository;
    
    public UserPortfolioServiceImpl(UserPortfolioRepository userPortfolioRepository, UserRepository userRepository) {
        this.userPortfolioRepository = userPortfolioRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public UserPortfolio createPortfolio(UserPortfolio portfolio) {
        if (portfolio.getUserId() != null && portfolio.getUser() == null) {
            User user = userRepository.findById(portfolio.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            portfolio.setUser(user);
        }
        return userPortfolioRepository.save(portfolio);
    }
    
    @Override
    public UserPortfolio updatePortfolio(Long id, UserPortfolio portfolio) {
        UserPortfolio existing = getPortfolioById(id);
        existing.setPortfolioName(portfolio.getPortfolioName());
        return userPortfolioRepository.save(existing);
    }
    
    @Override
    public UserPortfolio getPortfolioById(Long id) {
        return userPortfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
    }
    
    @Override
    public List<UserPortfolio> getPortfoliosByUser(Long userId) {
        return userPortfolioRepository.findByUserId(userId);
    }
    
    @Override
    public void deactivatePortfolio(Long id) {
        UserPortfolio portfolio = getPortfolioById(id);
        portfolio.setActive(false);
        userPortfolioRepository.save(portfolio);
    }
}
