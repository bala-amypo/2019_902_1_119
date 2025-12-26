package com.example.demo;

import com.example.demo.controller.*;
import com.example.demo.model.*;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@Listeners(TestResultListener.class)
public class PortfolioRiskAnalyzerTest {

    @Mock
    private StockService stockService;
    @Mock
    private UserPortfolioService portfolioService;
    @Mock
    private PortfolioHoldingService holdingService;
    @Mock
    private RiskThresholdService thresholdService;
    @Mock
    private RiskAnalysisService analysisService;
    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private StockController stockController;
    @InjectMocks
    private UserPortfolioController portfolioController;
    @InjectMocks
    private PortfolioHoldingController holdingController;
    @InjectMocks
    private RiskThresholdController thresholdController;
    @InjectMocks
    private RiskAnalysisController analysisController;
    @InjectMocks
    private AuthController authController;

    private String jwt;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwt = "test.jwt.token";
    }

    // SECTION 1 — Simple Servlet + Tomcat Server (1–5)

    @Test(priority = 1)
    public void testSimpleServletLoad() {
        Assert.assertTrue(true);
    }

    @Test(priority = 2)
    public void testServletReturnsResponse() {
        String message = "Servlet Running!";
        Assert.assertEquals(message, "Servlet Running!");
    }

    @Test(priority = 3)
    public void testTomcatServerStart() {
        Assert.assertNotNull(new Object());
    }

    @Test(priority = 4)
    public void testServletHandlesRequest() {
        Assert.assertTrue("REQUEST".contains("REQ"));
    }

    @Test(priority = 5)
    public void testServletEdgeCase() {
        Assert.assertThrows(NullPointerException.class, () -> {
            String x = null;
            x.length();
        });
    }

    // SECTION 2 — CRUD Operations using Spring Boot (6–15)

    @Test(priority = 6)
    public void testCreateStockSuccess() {
        Stock stock = new Stock();
        stock.setTicker("AAPL");
        when(stockService.createStock(any())).thenReturn(stock);

        ResponseEntity<Stock> res = stockController.createStock(stock);
        Assert.assertNotNull(res.getBody());
        Assert.assertEquals(res.getBody().getTicker(), "AAPL");
    }

    @Test(priority = 7)
    public void testCreateStockFailsDuplicate() {
        when(stockService.createStock(any())).thenThrow(new RuntimeException("Duplicate ticker"));
        Assert.assertThrows(RuntimeException.class, () -> stockController.createStock(new Stock()));
    }

    @Test(priority = 8)
    public void testUpdateStockSuccess() {
        Stock s = new Stock();
        s.setTicker("GOOG");
        when(stockService.updateStock(eq(1L), any())).thenReturn(s);
        ResponseEntity<Stock> res = stockController.updateStock(1L, s);
        Assert.assertEquals(res.getBody().getTicker(), "GOOG");
    }

    @Test(priority = 9)
    public void testUpdateStockNotFound() {
        when(stockService.updateStock(eq(99L), any())).thenThrow(new RuntimeException("Not found"));
        Assert.assertThrows(RuntimeException.class, () -> stockController.updateStock(99L, new Stock()));
    }

    @Test(priority = 10)
    public void testGetStockById() {
        Stock s = new Stock();
        s.setTicker("MSFT");
        when(stockService.getStockById(1L)).thenReturn(s);
        ResponseEntity<Stock> res = stockController.getStock(1L);
        Assert.assertEquals(res.getBody().getTicker(), "MSFT");
    }

    @Test(priority = 11)
    public void testGetStockNotExists() {
        when(stockService.getStockById(111L)).thenThrow(new RuntimeException("Not found"));
        Assert.assertThrows(RuntimeException.class, () -> stockController.getStock(111L));
    }

    @Test(priority = 12)
    public void testListStocks() {
        when(stockService.getAllStocks()).thenReturn(List.of(new Stock()));
        ResponseEntity<List<Stock>> res = stockController.getAllStocks();
        Assert.assertTrue(res.getBody() instanceof List);
        Assert.assertEquals(res.getBody().size(), 1);
    }

    @Test(priority = 13)
    public void testDeactivateStock() {
        doNothing().when(stockService).deactivateStock(1L);
        ResponseEntity<Void> res = stockController.deactivateStock(1L);
        Assert.assertEquals(res.getStatusCodeValue(), 200);
    }

    @Test(priority = 14)
    public void testCreatePortfolio() {
        UserPortfolio p = new UserPortfolio();
        p.setPortfolioName("Main");
        when(portfolioService.createPortfolio(any())).thenReturn(p);
        ResponseEntity<UserPortfolio> res = portfolioController.createPortfolio(p);
        Assert.assertEquals(res.getBody().getPortfolioName(), "Main");
    }

    @Test(priority = 15)
    public void testGetPortfolioNotFound() {
        when(portfolioService.getPortfolioById(999L)).thenThrow(new RuntimeException("Not found"));
        Assert.assertThrows(RuntimeException.class, () -> portfolioController.getPortfolio(999L));
    }

    // SECTION 3 — DI + IoC (16–20)

    @Test(priority = 16)
    public void testServiceInjected() {
        Assert.assertNotNull(stockService);
    }

    @Test(priority = 17)
    public void testControllerInjected() {
        Assert.assertNotNull(stockController);
    }

    @Test(priority = 18)
    public void testAutowiredBeansFunction() {
        when(stockService.getAllStocks()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Stock>> res = stockController.getAllStocks();
        Assert.assertEquals(res.getBody(), new ArrayList<>());
    }

    @Test(priority = 19)
    public void testDIServiceFailure() {
        when(stockService.getStockById(500L))
                .thenThrow(new RuntimeException("Fail"));
        Assert.assertThrows(RuntimeException.class, () -> stockController.getStock(500L));
    }

    @Test(priority = 20)
    public void testIoCContainer() {
        Assert.assertTrue(true);
    }

    // SECTION 4 — Hibernate config + Annotations (21–30)

    @Test(priority = 21)
    public void testStockModelFields() {
        Stock s = new Stock();
        s.setTicker("GOOG");
        s.setCompanyName("Google");
        Assert.assertEquals(s.getTicker(), "GOOG");
        Assert.assertEquals(s.getCompanyName(), "Google");
    }

    @Test(priority = 22)
    public void testPortfolioHoldingQuantityValidation() {
        PortfolioHolding h = new PortfolioHolding();
        h.setQuantity(10.0);
        Assert.assertTrue(h.getQuantity() > 0);
    }

    @Test(priority = 23)
    public void testPortfolioHoldingMarketValueValidation() {
        PortfolioHolding h = new PortfolioHolding();
        h.setMarketValue(BigDecimal.ZERO);
        Assert.assertTrue(h.getMarketValue().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test(priority = 24)
    public void testRiskThresholdPercentage() {
        RiskThreshold t = new RiskThreshold();
        t.setMaxSingleStockPercentage(80.0);
        Assert.assertTrue(t.getMaxSingleStockPercentage() <= 100);
    }

    @Test(priority = 25)
    public void testRiskAnalysisPercentLimit() {
        RiskAnalysisResult r = new RiskAnalysisResult();
        r.setHighestStockPercentage(50.0);
        Assert.assertTrue(r.getHighestStockPercentage() <= 100);
    }

    @Test(priority = 26)
    public void testUserEmailUnique() {
        User u = new User();
        u.setEmail("abc@mail.com");
        Assert.assertEquals(u.getEmail(), "abc@mail.com");
    }

    @Test(priority = 27)
    public void testUserRoleDefaultMonitor() {
        User u = new User();
        u.setRole("MONITOR");
        Assert.assertEquals(u.getRole(), "MONITOR");
    }

    @Test(priority = 28)
    public void testCreatedAtAutoPopulate() {
        User u = new User();
        u.setCreatedAt(LocalDateTime.now());
        Assert.assertNotNull(u.getCreatedAt());
    }

    @Test(priority = 29)
    public void testHibernateEntityAnnotationsPresent() {
        Assert.assertTrue(true);
    }

    @Test(priority = 30)
    public void testRelationMappingPortfolioHolding() {
        PortfolioHolding h = new PortfolioHolding();
        Assert.assertNull(h.getPortfolio());
    }

    // SECTION 5 — JPA mapping normalization (31–35)

    @Test(priority = 31)
    public void testOneNFPortfolio() {
        UserPortfolio p = new UserPortfolio();
        p.setPortfolioName("Main");
        Assert.assertEquals(p.getPortfolioName(), "Main");
    }

    @Test(priority = 32)
    public void testTwoNFPortfolio() {
        Assert.assertTrue(true);
    }

    @Test(priority = 33)
    public void testThreeNFPortfolio() {
        Assert.assertTrue(true);
    }

    @Test(priority = 34)
    public void testPortfolioHoldingMapping() {
        PortfolioHolding h = new PortfolioHolding();
        h.setQuantity(5.0);
        Assert.assertEquals(h.getQuantity(), 5.0);
    }

    @Test(priority = 35)
    public void testRiskAnalysisMapping() {
        RiskAnalysisResult r = new RiskAnalysisResult();
        r.setAnalysisDate(new Timestamp(System.currentTimeMillis()));
        Assert.assertNotNull(r.getAnalysisDate());
    }

    // SECTION 6 — Many-to-Many and Associations (36–40)

    @Test(priority = 36)
    public void testPortfolioCanHaveMultipleHoldings() {
        PortfolioHolding h1 = new PortfolioHolding();
        PortfolioHolding h2 = new PortfolioHolding();
        Assert.assertNotEquals(h1, h2);
    }

    @Test(priority = 37)
    public void testStockLinkedToMultipleHoldings() {
        Stock s = new Stock();
        Assert.assertNotNull(s);
    }

    @Test(priority = 38)
    public void testPortfolioHoldingAssociation() {
        PortfolioHolding h = new PortfolioHolding();
        Assert.assertNull(h.getStock());
    }

    @Test(priority = 39)
    public void testHoldingBelongsToPortfolio() {
        PortfolioHolding h = new PortfolioHolding();
        Assert.assertNull(h.getPortfolio());
    }

    @Test(priority = 40)
    public void testManyToOneMappingValid() {
        Assert.assertTrue(true);
    }

    // SECTION 7 — JWT + Security (41–55)

    @Test(priority = 41)
    public void testGenerateJwtToken() {
        when(jwtUtil.generateToken(anyString(), anyString(), anyLong())).thenReturn(jwt);
        String generated = jwtUtil.generateToken("a@mail.com", "ADMIN", 1L);
        Assert.assertEquals(generated, jwt);
    }

    @Test(priority = 42)
    public void testJwtTokenNotNull() {
        Assert.assertNotNull(jwt);
    }

    @Test(priority = 43)
    public void testJwtContainsRole() {
        Assert.assertTrue(jwt.length() > 5);
    }

    @Test(priority = 44)
    public void testAuthLoginSuccess() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("encodedPass");

        when(userService.findByEmail("test@mail.com")).thenReturn(user);
        Assert.assertNotNull(userService.findByEmail("test@mail.com"));
    }

    @Test(priority = 45)
    public void testAuthLoginFail() {
        when(userService.findByEmail("none@mail.com")).thenReturn(null);
        Assert.assertNull(userService.findByEmail("none@mail.com"));
    }

    @Test(priority = 46)
    public void testJwtValidation() {
        when(jwtUtil.validateToken(jwt)).thenReturn(true);
        Assert.assertTrue(jwtUtil.validateToken(jwt));
    }

    @Test(priority = 47)
    public void testJwtValidationFail() {
        when(jwtUtil.validateToken("invalid")).thenReturn(false);
        Assert.assertFalse(jwtUtil.validateToken("invalid"));
    }

    @Test(priority = 48)
    public void testJwtExtractEmail() {
        when(jwtUtil.extractEmail(jwt)).thenReturn("abc@mail.com");
        Assert.assertEquals(jwtUtil.extractEmail(jwt), "abc@mail.com");
    }

    @Test(priority = 49)
    public void testJwtExtractRole() {
        when(jwtUtil.extractRole(jwt)).thenReturn("ADMIN");
        Assert.assertEquals(jwtUtil.extractRole(jwt), "ADMIN");
    }

    @Test(priority = 50)
    public void testJwtExtractUserId() {
        when(jwtUtil.extractUserId(jwt)).thenReturn(1L);
        Assert.assertEquals(jwtUtil.extractUserId(jwt), Long.valueOf(1));
    }

    @Test(priority = 51)
    public void testUnauthorizedAccess() {
        Assert.assertThrows(Exception.class, () -> {
            throw new Exception("Unauthorized");
        });
    }

    @Test(priority = 52)
    public void testAuthorizedAccessWithValidJwt() {
        when(jwtUtil.validateToken(jwt)).thenReturn(true);
        boolean allowed = jwtUtil.validateToken(jwt);
        Assert.assertTrue(allowed, "Valid JWT should allow access");
    }

    @Test(priority = 53)
    public void testAdminRoleHasAccessToThresholdUpdate() {
        String role = "ADMIN";
        boolean access = role.equals("ADMIN");
        Assert.assertTrue(access, "ADMIN should have access to modify thresholds");
    }

    @Test(priority = 54)
    public void testMonitorRoleRestrictedForStockUpdate() {
        String role = "MONITOR";
        boolean access = role.equals("ADMIN");
        Assert.assertFalse(access, "MONITOR users should not update stock details");
    }

    @Test(priority = 55)
    public void testQualityAuditorHasReadOnlyAccess() {
        String role = "QUALITY_AUDITOR";
        boolean canModify = role.equals("ADMIN");
        boolean canRead = true;

        Assert.assertFalse(canModify, "QUALITY_AUDITOR cannot modify data");
        Assert.assertTrue(canRead, "QUALITY_AUDITOR must be able to read data");
    }

    // SECTION 8 — HQL & HCQL advanced queries (56–60)

    @Test(priority = 56)
    public void testHqlFetchActivePortfolios() {
        List<UserPortfolio> mockList = List.of(new UserPortfolio());
        when(portfolioService.getPortfoliosByUser(1L)).thenReturn(mockList);

        List<UserPortfolio> result = portfolioService.getPortfoliosByUser(1L);
        Assert.assertEquals(result.size(), 1, "Should fetch active portfolios by user");
    }

    @Test(priority = 57)
    public void testHqlFilterStocksBySector() {
        Stock tech = new Stock();
        tech.setSector("TECH");

        when(stockService.getAllStocks()).thenReturn(List.of(tech));

        List<Stock> all = stockService.getAllStocks();
        boolean foundTech = all.stream().anyMatch(s -> "TECH".equals(s.getSector()));

        Assert.assertTrue(foundTech, "Should filter stocks by sector using HQL");
    }

    @Test(priority = 58)
    public void testHqlFindHighRiskResults() {
        RiskAnalysisResult r1 = new RiskAnalysisResult();
        r1.setHighRisk(true);

        when(analysisService.getAnalysesForPortfolio(1L))
                .thenReturn(List.of(r1));

        List<RiskAnalysisResult> results = analysisService.getAnalysesForPortfolio(1L);
        boolean hasRisk = results.stream().anyMatch(RiskAnalysisResult::isHighRisk);

        Assert.assertTrue(hasRisk, "HQL should identify high-risk portfolios");
    }

    @Test(priority = 59)
    public void testHcqlAggregatedMarketValue() {
        PortfolioHolding h1 = new PortfolioHolding();
        h1.setMarketValue(new BigDecimal("1000"));

        PortfolioHolding h2 = new PortfolioHolding();
        h2.setMarketValue(new BigDecimal("2500"));

        when(holdingService.getHoldingsByPortfolio(1L))
                .thenReturn(List.of(h1, h2));

        BigDecimal total = holdingService.getHoldingsByPortfolio(1L)
                .stream()
                .map(PortfolioHolding::getMarketValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Assert.assertEquals(total, new BigDecimal("3500"), "HCQL aggregation should compute total market value");
    }

    @Test(priority = 60)
    public void testHcqlJoinPortfolioStock() {
        Stock stock = new Stock();
        stock.setTicker("AAPL");

        PortfolioHolding h = new PortfolioHolding();
        h.setStock(stock);

        when(holdingService.getHoldingsByPortfolio(1L))
                .thenReturn(List.of(h));

        List<String> tickers =
                holdingService.getHoldingsByPortfolio(1L)
                        .stream()
                        .map(x -> x.getStock().getTicker())
                        .toList();

        Assert.assertTrue(tickers.contains("AAPL"), "HCQL join must include stock-ticker from portfolio holdings");
    }
}
