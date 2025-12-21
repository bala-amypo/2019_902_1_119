public class AuthResponse{
    
}

repository:
user:

userPortfolio:


stock:


portfolioholding:

riskAnalysisResult:


RiskThreshold:


Service:
PortfolioHolding:


IMPL:


RiskAnalysis:

IMPL:


RiskThresho;ld:


IMPL:

Stock:

IMPL:


UserPortfolio:

IMPL:
user:


IMPL:

Exception:
Global:
ResourceNotFound:

model:

portfolioHolding:


RiskAnalysis:


RiskThreshold:
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_thresholds")
public class RiskThreshold {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private UserPortfolio portfolio;
    
    private String thresholdName;
    
    private Double maxSingleStockPercentage;
    
    private Double maxSectorPercentage;
    
    private Double maxOverallVolatility;
    
    private Boolean active = true;
    
    public RiskThreshold() {}
    
    public RiskThreshold(UserPortfolio portfolio, Double maxSingleStockPercentage, Double maxOverallVolatility) {
        this.portfolio = portfolio;
        this.maxSingleStockPercentage = maxSingleStockPercentage;
        this.maxOverallVolatility = maxOverallVolatility;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public UserPortfolio getPortfolio() { return portfolio; }
    public void setPortfolio(UserPortfolio portfolio) { this.portfolio = portfolio; }
    
    public String getThresholdName() { return thresholdName; }
    public void setThresholdName(String thresholdName) { this.thresholdName = thresholdName; }
    
    public Double getMaxSingleStockPercentage() { return maxSingleStockPercentage; }
    public void setMaxSingleStockPercentage(Double maxSingleStockPercentage) { this.maxSingleStockPercentage = maxSingleStockPercentage; }
    
    public Double getMaxSectorPercentage() { return maxSectorPercentage; }
    public void setMaxSectorPercentage(Double maxSectorPercentage) { this.maxSectorPercentage = maxSectorPercentage; }
    
    public Double getMaxOverallVolatility() { return maxOverallVolatility; }
    public void setMaxOverallVolatility(Double maxOverallVolatility) { this.maxOverallVolatility = maxOverallVolatility; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}

Stock:

package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "stocks")
public class Stock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String ticker;
    
    private String companyName;
    
    private String sector;
    
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<PortfolioHolding> holdings;
    
    public Stock() {}
    
    public Stock(String ticker, String companyName, String sector, Boolean isActive) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.sector = sector;
        this.isActive = isActive;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public List<PortfolioHolding> getHoldings() { return holdings; }
    public void setHoldings(List<PortfolioHolding> holdings) { this.holdings = holdings; }
}

user:
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private String fullName;
    
    private String role = "MONITOR";
    
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPortfolio> portfolios;
    
    public User() {}
    
    public User(String email, String password, String role, LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public List<UserPortfolio> getPortfolios() { return portfolios; }
    public void setPortfolios(List<UserPortfolio> portfolios) { this.portfolios = portfolios; }
}

UserPortfolio:
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "user_portfolios")
public class UserPortfolio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String portfolioName;
    
    private LocalDateTime createdAt;
    
    private Timestamp updatedAt;
    
    private Boolean active = true;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioHolding> holdings;
    
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<RiskAnalysisResult> analysisResults;
    
    public UserPortfolio() {}
    
    public UserPortfolio(User user, String portfolioName, LocalDateTime createdAt) {
        this.user = user;
        this.portfolioName = portfolioName;
        this.createdAt = createdAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Long getUserId() { return user != null ? user.getId() : null; }
    public void setUserId(Long userId) { 
        // This is a computed property, actual setting is done via setUser
    }
    
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public List<PortfolioHolding> getHoldings() { return holdings; }
    public void setHoldings(List<PortfolioHolding> holdings) { this.holdings = holdings; }
    
    public List<RiskAnalysisResult> getAnalysisResults() { return analysisResults; }
    public void setAnalysisResults(List<RiskAnalysisResult> analysisResults) { this.analysisResults = analysisResults; }
}
servlet:
SimpleStatus;
package com.example.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/status")
public class SimpleStatusServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setStatus(200);
        response.getWriter().write("Servlet Running!");
    }
}


















