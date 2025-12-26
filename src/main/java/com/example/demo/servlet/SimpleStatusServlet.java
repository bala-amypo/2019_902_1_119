// package com.example.demo.servlet;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;

// @WebServlet(urlPatterns = "/status")
// public class SimpleStatusServlet extends HttpServlet {
    
//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException {
//         response.setStatus(200);
//         response.getWriter().write("Servlet Running!");
//     }
// }

// package com.example.demo.servlet;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.io.PrintWriter;

// @WebServlet(urlPatterns = "/status")
// public class SimpleStatusServlet extends HttpServlet {
//     @Override
//     protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
//             throws ServletException, IOException {
//         resp.setStatus(200);
//         resp.setContentType("text/plain");
//         PrintWriter out = resp.getWriter();
//         out.print("Servlet Running!");
//         out.flush();
//     }
// }














