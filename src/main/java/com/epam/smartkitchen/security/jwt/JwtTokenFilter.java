package com.epam.smartkitchen.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.catalina.connector.Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.multipart.support.RequestPartServletServerHttpRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        List<String> tokens = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        if (tokens != null && jwtTokenProvider.validateToken(tokens)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(tokens.get(0));
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            if (tokens.size() == 2) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setHeader("accessToken", tokens.get(0));
                response.setHeader("refreshToken", tokens.get(1));
                filterChain.doFilter(servletRequest, response);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}