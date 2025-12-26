package com.example.demo.repository;

import com.example.demo.model.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserPortfolioRepository extends JpaRepository<UserPortfolio, Long> {
    List<UserPortfolio> findByUserId(Long userId);
}