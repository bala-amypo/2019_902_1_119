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

    private final UserPortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public UserPortfolioServiceImpl(UserPortfolioRepository portfolioRepository,
                                    UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserPortfolio createPortfolio(UserPortfolio portfolio) {
        User user = userRepository.findById(
                portfolio.getUser().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
        portfolio.setUser(user);
        return portfolioRepository.save(portfolio);
    }

    @Override
    public UserPortfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Portfolio not found"));
    }

    @Override
    public List<UserPortfolio> getPortfoliosByUser(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }
}
