controller:
portfolioHolding:

riskAn:

RiskThres:

stock:

User:
package com.example.demo.controller;

import com.example.demo.model.UserPortfolio;
import com.example.demo.service.UserPortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@Tag(name = "Portfolios", description = "Portfolio management endpoints")
public class UserPortfolioController {
    
    private final UserPortfolioService userPortfolioService;
    
    public UserPortfolioController(UserPortfolioService userPortfolioService) {
        this.userPortfolioService = userPortfolioService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new portfolio")
    public ResponseEntity<UserPortfolio> createPortfolio(@RequestBody UserPortfolio portfolio) {
        UserPortfolio createdPortfolio = userPortfolioService.createPortfolio(portfolio);
        return ResponseEntity.ok(createdPortfolio);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing portfolio")
    public ResponseEntity<UserPortfolio> updatePortfolio(@PathVariable Long id, @RequestBody UserPortfolio portfolio) {
        UserPortfolio updatedPortfolio = userPortfolioService.updatePortfolio(id, portfolio);
        return ResponseEntity.ok(updatedPortfolio);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get portfolio by ID")
    public ResponseEntity<UserPortfolio> getPortfolio(@PathVariable Long id) {
        UserPortfolio portfolio = userPortfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get portfolios for a user")
    public ResponseEntity<List<UserPortfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        List<UserPortfolio> portfolios = userPortfolioService.getPortfoliosByUser(userId);
        return ResponseEntity.ok(portfolios);
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a portfolio")
    public ResponseEntity<String> deactivatePortfolio(@PathVariable Long id) {
        userPortfolioService.deactivatePortfolio(id);
        return ResponseEntity.ok("Portfolio deactivated successfully");
    }
}

application pro:
spring.application.name=demo
server.port=8081

# H2 Database Configuration (for testing)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console (for debugging)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html


# spring.application.name=demo
# spring.datasource.url=jdbc:mysql://localhost:3306/transport_pro?createDatabaseIfNotExist=true
# spring.datasource.username=root
# spring.datasource.password=root
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# spring.jpa.hibernate.ddl-auto=update
# spring.jpa.show-sql=true
# spring.jpa.properties.hibernate.format_sql=true
# spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

pom.xml:
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.2.0</version>
		<relativePath/>
	</parent>
	<groupId>com.example</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo</name>
	<description>Stock Portfolio Risk Analyzer</description>
	<properties>
		<java.version>17</java.version>
	</properties>
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
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.2.0</version>
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
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
