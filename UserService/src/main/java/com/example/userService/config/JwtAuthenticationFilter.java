package com.example.userService.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String authHeader = request.getHeader("Authorization");
       if(authHeader != null && authHeader.startsWith("Bearer")){
           String token = authHeader.substring(7);
           try{
               String username = jwtUtil.validateToken(token);
               UsernamePasswordAuthenticationToken authencation = new UsernamePasswordAuthenticationToken(username,null,null);
               authencation.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authencation);
           }catch (IllegalArgumentException e){
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
               return;
           }
       }
       filterChain.doFilter(request,response);
    }
}
