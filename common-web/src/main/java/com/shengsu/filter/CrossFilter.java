package com.shengsu.filter;
  
import java.io.IOException;  
  
import javax.servlet.FilterChain;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.springframework.web.filter.OncePerRequestFilter;  
  
public class CrossFilter extends OncePerRequestFilter {  
    @Override  
    protected void doFilterInternal(HttpServletRequest request,  
            HttpServletResponse response, FilterChain filterChain)  
            throws ServletException, IOException {  
        // if (request.getHeader("Access-Control-Request-Method") != null &&  
        // "OPTIONS".equals(request.getMethod())) {  
        // CORS "pre-flight" request  
    	response.setHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Access-Control-Allow-Origin", "*");  
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS"); 
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,Authorization");  
        response.addHeader("Access-Control-Max-Age", "1800");// 30 min  
        // }  
        filterChain.doFilter(request, response);  
    }  
  
} 