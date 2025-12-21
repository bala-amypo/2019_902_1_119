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

Stock:

user:

UserPortfolio:
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


















