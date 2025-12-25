
package com.example.demo.repository;

import com.example.demo.model.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserPortfolioRepository extends JpaRepository<UserPortfolio, Long> {
    List<UserPortfolio> findByUserId(Long userId);
}

portHolding:
package com.example.demo.repository;

import com.example.demo.model.PortfolioHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioHoldingRepository extends JpaRepository<PortfolioHolding, Long> {
    List<PortfolioHolding> findByPortfolioId(Long portfolioId);
}

RiskThreshold:
package com.example.demo.repository;

import com.example.demo.model.RiskThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RiskThresholdRepository extends JpaRepository<RiskThreshold, Long> {
    Optional<RiskThreshold> findByIsActiveTrue();
}

RiskAnalysis:
package com.example.demo.repository;

import com.example.demo.model.RiskAnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiskAnalysisResultRepository extends JpaRepository<RiskAnalysisResult, Long> {
    List<RiskAnalysisResult> findByPortfolioId(Long portfolioId);
}

User:
package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}


package com.example.demo.service;

import com.example.demo.model.PortfolioHolding;
import java.util.List;

public interface PortfolioHoldingService {
    PortfolioHolding createHolding(PortfolioHolding holding);
    PortfolioHolding updateHolding(Long id, PortfolioHolding holding);
    PortfolioHolding getHoldingById(Long id);
    List<PortfolioHolding> getHoldingsByPortfolio(Long portfolioId);
    void deleteHolding(Long id);
}

IMPL:
RiskThreshold:

package com.example.demo.service;

import com.example.demo.model.RiskThreshold;

public interface RiskThresholdService {
    RiskThreshold createThreshold(RiskThreshold threshold);
    RiskThreshold updateThreshold(Long id, RiskThreshold threshold);
    RiskThreshold getActiveThreshold();
    RiskThreshold getThresholdById(Long id);
    java.util.List<RiskThreshold> getAllThresholds();
}
IMPL:
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RiskThreshold;
import com.example.demo.repository.RiskThresholdRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskThresholdServiceImpl implements RiskThresholdService {
    private final RiskThresholdRepository riskThresholdRepository;

    public RiskThresholdServiceImpl(RiskThresholdRepository riskThresholdRepository) {
        this.riskThresholdRepository = riskThresholdRepository;
    }

    @Override
    public RiskThreshold createThreshold(RiskThreshold threshold) {
        if (threshold.getMaxSingleStockPercentage() < 0 || threshold.getMaxSingleStockPercentage() > 100) {
            throw new IllegalArgumentException("Max single stock percentage must be between 0 and 100");
        }
        if (threshold.getMaxSectorPercentage() < 0 || threshold.getMaxSectorPercentage() > 100) {
            throw new IllegalArgumentException("Max sector percentage must be between 0 and 100");
        }
        return riskThresholdRepository.save(threshold);
    }

    @Override
    public RiskThreshold updateThreshold(Long id, RiskThreshold threshold) {
        RiskThreshold existing = getThresholdById(id);
        threshold.setId(id);
        return riskThresholdRepository.save(threshold);
    }

    @Override
    public RiskThreshold getActiveThreshold() {
        return riskThresholdRepository.findByIsActiveTrue()
            .orElseThrow(() -> new ResourceNotFoundException("Active threshold not found"));
    }

    @Override
    public RiskThreshold getThresholdById(Long id) {
        return riskThresholdRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Threshold not found"));
    }

    @Override
    public List<RiskThreshold> getAllThresholds() {
        return riskThresholdRepository.findAll();
    }
}

RiskAnalysis:
package com.example.demo.service;

import com.example.demo.model.RiskAnalysisResult;
import java.util.List;

public interface RiskAnalysisService {
    RiskAnalysisResult analyzePortfolio(Long portfolioId);
    RiskAnalysisResult getAnalysisById(Long id);
    List<RiskAnalysisResult> getAnalysesForPortfolio(Long portfolioId);
}
IMPL:
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RiskAnalysisServiceImpl implements RiskAnalysisService {
    private final RiskAnalysisResultRepository riskAnalysisResultRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    private final PortfolioHoldingRepository portfolioHoldingRepository;
    private final RiskThresholdRepository riskThresholdRepository;

    public RiskAnalysisServiceImpl(RiskAnalysisResultRepository riskAnalysisResultRepository,
                                 UserPortfolioRepository userPortfolioRepository,
                                 PortfolioHoldingRepository portfolioHoldingRepository,
                                 RiskThresholdRepository riskThresholdRepository) {
        this.riskAnalysisResultRepository = riskAnalysisResultRepository;
        this.userPortfolioRepository = userPortfolioRepository;
        this.portfolioHoldingRepository = portfolioHoldingRepository;
        this.riskThresholdRepository = riskThresholdRepository;
    }

    @Override
    public RiskAnalysisResult analyzePortfolio(Long portfolioId) {
        userPortfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        
        List<PortfolioHolding> holdings = portfolioHoldingRepository.findByPortfolioId(portfolioId);
        RiskThreshold threshold = riskThresholdRepository.findByIsActiveTrue()
            .orElseThrow(() -> new ResourceNotFoundException("Active threshold not found"));

        BigDecimal totalValue = holdings.stream()
            .map(PortfolioHolding::getMarketValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalValue.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Portfolio has no value");
        }

        // Calculate highest stock percentage
        Map<Long, BigDecimal> stockValues = holdings.stream()
            .collect(Collectors.toMap(h -> h.getStockId(), PortfolioHolding::getMarketValue));
        
        double highestStockPercentage = stockValues.values().stream()
            .mapToDouble(v -> v.divide(totalValue, 4, BigDecimal.ROUND_HALF_UP).doubleValue() * 100)
            .max().orElse(0.0);

        // Calculate highest sector percentage (simplified)
        double highestSectorPercentage = highestStockPercentage * 1.5; // Mock calculation

        boolean isHighRisk = highestStockPercentage > threshold.getMaxSingleStockPercentage() ||
                           highestSectorPercentage > threshold.getMaxSectorPercentage();

        RiskAnalysisResult result = new RiskAnalysisResult();
        result.setPortfolioId(portfolioId);
        result.setAnalysisDate(LocalDateTime.now());
        result.setHighestStockPercentage(highestStockPercentage);
        result.setHighestSectorPercentage(highestSectorPercentage);
        result.setIsHighRisk(isHighRisk);
        result.setNotes("Risk analysis completed");

        return riskAnalysisResultRepository.save(result);
    }

    @Override
    public RiskAnalysisResult getAnalysisById(Long id) {
        return riskAnalysisResultRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Analysis not found"));
    }

    @Override
    public List<RiskAnalysisResult> getAnalysesForPortfolio(Long portfolioId) {
        return riskAnalysisResultRepository.findByPortfolioId(portfolioId);
    }
}
User:
package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
}
IMPL:
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (user.getRole() == null) {
            user.setRole("MONITOR");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}


config:
openapi:

securiyyt:
package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            );
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
