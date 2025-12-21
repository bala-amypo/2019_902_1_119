package com.example.demo.repository;

import com.example.demo.model.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserPortfolioRepository extends JpaRepository<UserPortfolio, Long> {
    @Query("SELECT p FROM UserPortfolio p WHERE p.user.id = :userId")
    List<UserPortfolio> findByUserId(@Param("userId") Long userId);
}