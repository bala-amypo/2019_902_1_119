Main:
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
Servlet:


Model:
User

Stock:

UserPOrt:

POrtHolding:
RiskThreshold:
RiskAnalysis:


config:
SEcurityConfig:


UserRepository.java
java
StockRepository.java
java
UserPortfolioRepository.java
java
PortfolioHoldingRepository.java
java

RiskThresholdRepository.java
java
RiskAnalysisResultRepository.java
java

Service Interfaces
UserService.java
java

StockService.java
java

UserPortfolioService.java
java

PortfolioHoldingService.java
java

RiskThresholdService.java
java

RiskAnalysisService.java
java

Service Implementations (Constructor Injection - Test Suite Required Order)
UserServiceImpl.java
java
StockServiceImpl.java (Exact Constructor Order for Tests)
java
UserPortfolioServiceImpl.java
java
PortfolioHoldingServiceImpl.java
java
RiskThresholdServiceImpl.java
java
RiskAnalysisServiceImpl.java
java
DTOs
LoginRequest.java
java

AuthResponse.java
java
StockController.java
java
UserPortfolioController.java
java
PortfolioHoldingController.java
java
RiskThresholdController.java
java
RiskAnalysisController.java
java
AuthController.java
java

JwtUtil.java (Required Constructor for Tests)
java
JwtAuthenticationFilter.java
java
CustomUserDetailsService.java
java
Exception Classes
ResourceNotFoundException.java
java
GlobalExceptionHandler.java
java
TestResultListener.java (Required for Test Output)
java
package com.example.demo;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestResultListener implements ITestListener {
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " - PASS");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " - FAIL");
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " - SKIP");
    }
}
Updated pom.xml Dependencies
xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.11.5</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.11.5</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.11.5</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.2.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.8.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>




​





​






