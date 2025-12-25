

Entity:
User:

stock:

userport:


potHolding:


riskThreshold:

riskAnalysis:

DTO:
LoginREquest:


AuthResponse:


repositories:
Stock:


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

IMPL:

UserPort:

IMPL:

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

