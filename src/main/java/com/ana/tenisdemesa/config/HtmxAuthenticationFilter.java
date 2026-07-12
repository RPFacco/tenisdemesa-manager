package com.ana.tenisdemesa.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class HtmxAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String hxHeader = request.getHeader("HX-Request");
        if ("true".equals(hxHeader)) {
            request.setAttribute("isHxRequest", true);
        }
        filterChain.doFilter(request, response);
    }
}
