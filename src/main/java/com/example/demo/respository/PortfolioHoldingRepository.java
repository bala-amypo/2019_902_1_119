package com.example.demo.repository;

import com.example.demo.model.PortfolioHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioHoldingRepository extends JpaRepository<PortfolioHolding, Long> {
    List<PortfolioHolding> findByPortfolioId(Long portfolioId);
}