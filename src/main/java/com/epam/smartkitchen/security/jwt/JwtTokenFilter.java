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
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        List<String> updateTokens = null;
        boolean isValidToken = false;
        if (token != null) {
            try {
                isValidToken = jwtTokenProvider.validateToken(token);
            } catch (ExpiredJwtException e) {
                updateTokens = jwtTokenProvider.resolveRefreshToken();
                if (updateTokens != null){
                    isValidToken = true;
                    token = updateTokens.get(0);
                }
            }
            if (isValidToken) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            if (updateTokens != null && updateTokens.size() == 2) {
                HttpServletResponse response = jwtTokenProvider.setResponse(updateTokens,
                        (HttpServletResponse) servletResponse);
                filterChain.doFilter(servletRequest, response);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}