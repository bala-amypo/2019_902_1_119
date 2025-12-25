

Entity:
User:

stock:

userport:

package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_portfolios")
public class UserPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "portfolio_name", nullable = false)
    private String portfolioName;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    public UserPortfolio() {}
    
    public UserPortfolio(Long userId, String portfolioName, LocalDateTime createdAt) {
        this.userId = userId;
        this.portfolioName = portfolioName;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}

potHolding:
package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "portfolio_holdings")
public class PortfolioHolding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "portfolio_id")
    private Long portfolioId;
    
    @Column(name = "stock_id")
    private Long stockId;
    
    @Column(nullable = false)
    private Double quantity;
    
    @Column(name = "market_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal marketValue;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    public PortfolioHolding() {}
    
    public PortfolioHolding(Long portfolioId, Long stockId, Double quantity, BigDecimal marketValue) {
        this.portfolioId = portfolioId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.marketValue = marketValue;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    
    public Long getStockId() { return stockId; }
    public void setStockId(Long stockId) { this.stockId = stockId; }
    
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    
    public BigDecimal getMarketValue() { return marketValue; }
    public void setMarketValue(BigDecimal marketValue) { this.marketValue = marketValue; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}

riskThreshold:
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_thresholds")
public class RiskThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "threshold_name", unique = true, nullable = false)
    private String thresholdName;
    
    @Column(name = "max_single_stock_percentage", nullable = false)
    private Double maxSingleStockPercentage;
    
    @Column(name = "max_sector_percentage", nullable = false)
    private Double maxSectorPercentage;
    
    @Column(name = "is_active")
    private Boolean isActive = false;
    
    public RiskThreshold() {}
    
    public RiskThreshold(String thresholdName, Double maxSingleStockPercentage, Double maxSectorPercentage) {
        this.thresholdName = thresholdName;
        this.maxSingleStockPercentage = maxSingleStockPercentage;
        this.maxSectorPercentage = maxSectorPercentage;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getThresholdName() { return thresholdName; }
    public void setThresholdName(String thresholdName) { this.thresholdName = thresholdName; }
    
    public Double getMaxSingleStockPercentage() { return maxSingleStockPercentage; }
    public void setMaxSingleStockPercentage(Double maxSingleStockPercentage) { 
        this.maxSingleStockPercentage = maxSingleStockPercentage; 
    }
    
    public Double getMaxSectorPercentage() { return maxSectorPercentage; }
    public void setMaxSectorPercentage(Double maxSectorPercentage) { 
        this.maxSectorPercentage = maxSectorPercentage; 
    }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
riskAnalysis:
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_analysis_results")
public class RiskAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "portfolio_id")
    private Long portfolioId;
    
    @Column(name = "analysis_date")
    private LocalDateTime analysisDate;
    
    @Column(name = "highest_stock_percentage")
    private Double highestStockPercentage;
    
    @Column(name = "highest_sector_percentage")
    private Double highestSectorPercentage;
    
    @Column(name = "is_high_risk")
    private Boolean isHighRisk;
    
    private String notes;
    
    public RiskAnalysisResult() {}
    
    public RiskAnalysisResult(Long portfolioId, LocalDateTime analysisDate, 
                            Double highestStockPercentage, Double highestSectorPercentage, 
                            Boolean isHighRisk, String notes) {
        this.portfolioId = portfolioId;
        this.analysisDate = analysisDate;
        this.highestStockPercentage = highestStockPercentage;
        this.highestSectorPercentage = highestSectorPercentage;
        this.isHighRisk = isHighRisk;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    
    public LocalDateTime getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(LocalDateTime analysisDate) { this.analysisDate = analysisDate; }
    
    public Double getHighestStockPercentage() { return highestStockPercentage; }
    public void setHighestStockPercentage(Double highestStockPercentage) { this.highestStockPercentage = highestStockPercentage; }
    
    public Double getHighestSectorPercentage() { return highestSectorPercentage; }
    public void setHighestSectorPercentage(Double highestSectorPercentage) { this.highestSectorPercentage = highestSectorPercentage; }
    
    public Boolean getIsHighRisk() { return isHighRisk; }
    public void setIsHighRisk(Boolean isHighRisk) { this.isHighRisk = isHighRisk; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
DTO:
LoginREquest:

package com.example.demo.dto;

public class LoginRequest {
    private String email;
    private String password;
    
    // Constructors, Getters, Setters
    public LoginRequest() {}
    
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

AuthResponse:

package com.example.demo.dto;

public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String role;
    
    public AuthResponse() {}
    
    public AuthResponse(String token, Long userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
repositories:
Stock:
package com.example.demo.repository;

import com.example.demo.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByTicker(String ticker);
}

UsrPOrt:
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
Service:

StockService:
package com.example.demo.service;

import com.example.demo.model.Stock;
import java.util.List;

public interface StockService {
    Stock createStock(Stock stock);
    Stock updateStock(Long id, Stock stock);
    Stock getStockById(Long id);
    List<Stock> getAllStocks();
    void deactivateStock(Long id);
}

IMPL:
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock createStock(Stock stock) {
        if (stockRepository.findByTicker(stock.getTicker()).isPresent()) {
            throw new IllegalArgumentException("Duplicate ticker");
        }
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Long id, Stock stock) {
        Stock existing = stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        stock.setId(id);
        return stockRepository.save(stock);
    }

    @Override
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public void deactivateStock(Long id) {
        Stock stock = getStockById(id);
        stock.setIsActive(false);
        stockRepository.save(stock);
    }
}
UserPort:
package com.example.demo.service;

import com.example.demo.model.UserPortfolio;
import java.util.List;

public interface UserPortfolioService {
    UserPortfolio createPortfolio(UserPortfolio portfolio);
    UserPortfolio updatePortfolio(Long id, UserPortfolio portfolio);
    UserPortfolio getPortfolioById(Long id);
    List<UserPortfolio> getPortfoliosByUser(Long userId);
    void deactivatePortfolio(Long id);
}

IMPL:


package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserPortfolio;
import com.example.demo.repository.UserPortfolioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserPortfolioServiceImpl implements UserPortfolioService {
    private final UserPortfolioRepository userPortfolioRepository;

    public UserPortfolioServiceImpl(UserPortfolioRepository userPortfolioRepository) {
        this.userPortfolioRepository = userPortfolioRepository;
    }

    @Override
    public UserPortfolio createPortfolio(UserPortfolio portfolio) {
        portfolio.setCreatedAt(LocalDateTime.now());
        portfolio.setIsActive(true);
        return userPortfolioRepository.save(portfolio);
    }

    @Override
    public UserPortfolio updatePortfolio(Long id, UserPortfolio portfolio) {
        UserPortfolio existing = getPortfolioById(id);
        portfolio.setId(id);
        portfolio.setUpdatedAt(LocalDateTime.now());
        return userPortfolioRepository.save(portfolio);
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
        portfolio.setIsActive(false);
        userPortfolioRepository.save(portfolio);
    }
}
POrtHolding:

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
package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PortfolioHolding;
import com.example.demo.repository.PortfolioHoldingRepository;
import com.example.demo.repository.UserPortfolioRepository;
import com.example.demo.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PortfolioHoldingServiceImpl implements PortfolioHoldingService {
    private final PortfolioHoldingRepository portfolioHoldingRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    private final StockRepository stockRepository;

    public PortfolioHoldingServiceImpl(PortfolioHoldingRepository portfolioHoldingRepository,
                                     UserPortfolioRepository userPortfolioRepository,
                                     StockRepository stockRepository) {
        this.portfolioHoldingRepository = portfolioHoldingRepository;
        this.userPortfolioRepository = userPortfolioRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public PortfolioHolding createHolding(PortfolioHolding holding) {
        if (holding.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        if (holding.getMarketValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Market value must be >= 0");
        }
        userPortfolioRepository.findById(holding.getPortfolioId())
            .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
        stockRepository.findById(holding.getStockId())
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        holding.setLastUpdated(LocalDateTime.now());
        return portfolioHoldingRepository.save(holding);
    }

    @Override
    public PortfolioHolding updateHolding(Long id, PortfolioHolding holding) {
        PortfolioHolding existing = getHoldingById(id);
        holding.setId(id);
        holding.setLastUpdated(LocalDateTime.now());
        return portfolioHoldingRepository.save(holding);
    }

    @Override
    public PortfolioHolding getHoldingById(Long id) {
        return portfolioHoldingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Holding not found"));
    }

    @Override
    public List<PortfolioHolding> getHoldingsByPortfolio(Long portfolioId) {
        return portfolioHoldingRepository.findByPortfolioId(portfolioId);
    }

    @Override
    public void deleteHolding(Long id) {
        if (!portfolioHoldingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Holding not found");
        }
        portfolioHoldingRepository.deleteById(id);
    }
}
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
Security
JwtUtil.java:

package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {
    private final Key secretKey;
    private final long validityInMs;

    public JwtUtil(String secret, long validityInMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMs = validityInMs;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + validityInMs))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
Controller:
authController:
package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "User login and registration")
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                         UserDetailsService userDetailsService,
                         JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        
        User user = userService.findByEmail(request.getEmail());
        
        AuthResponse response = new AuthResponse(token, user.getId(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(response);
    }
}
Stock:
package com.example.demo.controller;

import com.example.demo.model.Stock;
import com.example.demo.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Stocks", description = "Stock management operations")
@RestController
@RequestMapping("/api/stocks")
@SecurityRequirement(name = "bearerAuth")
public class StockController {
    
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "Create new stock")
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock created = stockService.createStock(stock);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Update stock")
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        Stock updated = stockService.updateStock(id, stock);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get stock by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    @Operation(summary = "List all stocks")
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @Operation(summary = "Deactivate stock")
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateStock(@PathVariable Long id) {
        stockService.deactivateStock(id);
        return ResponseEntity.ok().build();
    }
}
UserPort:
package com.example.demo.controller;

import com.example.demo.model.UserPortfolio;
import com.example.demo.service.UserPortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Portfolios", description = "User portfolio management")
@RestController
@RequestMapping("/api/portfolios")
@SecurityRequirement(name = "bearerAuth")
public class UserPortfolioController {
    
    private final UserPortfolioService userPortfolioService;

    public UserPortfolioController(UserPortfolioService userPortfolioService) {
        this.userPortfolioService = userPortfolioService;
    }

    @Operation(summary = "Create portfolio")
    @PostMapping
    public ResponseEntity<UserPortfolio> createPortfolio(@RequestBody UserPortfolio portfolio) {
        UserPortfolio created = userPortfolioService.createPortfolio(portfolio);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Update portfolio")
    @PutMapping("/{id}")
    public ResponseEntity<UserPortfolio> updatePortfolio(@PathVariable Long id, @RequestBody UserPortfolio portfolio) {
        UserPortfolio updated = userPortfolioService.updatePortfolio(id, portfolio);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get portfolio by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserPortfolio> getPortfolioById(@PathVariable Long id) {
        UserPortfolio portfolio = userPortfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }

    @Operation(summary = "Get portfolios by user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPortfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        List<UserPortfolio> portfolios = userPortfolioService.getPortfoliosByUser(userId);
        return ResponseEntity.ok(portfolios);
    }

    @Operation(summary = "Deactivate portfolio")
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePortfolio(@PathVariable Long id) {
        userPortfolioService.deactivatePortfolio(id);
        return ResponseEntity.ok().build();
    }
}
config:
openapi:
package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Stock Portfolio Risk Analyzer API", 
                               version = "1.0", 
                               description = "API for managing stock portfolios and risk analysis"))
@SecurityScheme(name = "bearerAuth", 
                type = SecuritySchemeType.HTTP, 
                scheme = "bearer", 
                bearerFormat = "JWT")
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList(securitySchemeName);
        
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .scheme("bearer")))
            .addSecurityItem(securityRequirement);
    }
}
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
app.prop:

# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/stock_portfolio?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# JWT
jwt.secret=mySecretKeyForJWTGenerationAndValidation1234567890
jwt.expiration=86400000

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs

JwtAuthenticationFilter.java:

package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = null; // Will be injected via Spring
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
CustomerUserDetailsService.java:
package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = null; // Will be injected via Spring
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

