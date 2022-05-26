package com.epam.smartkitchen.security.jwt;

import antlr.MismatchedTokenException;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private Long validateMilliseconds;

    private final UserDetailsService userDetailsService;
    private final UserService userService;


    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaims(String email, UserType userType) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userType", userType);
        return claims;
    }

    public List<String> createTokens(String email, UserType userType) {
        List<String> tokens = new ArrayList<>();
        tokens.add(accessToken(email, userType));
        tokens.add(refreshToken());
        return tokens;
    }

    private String accessToken(String email, UserType userType) {
        Claims claims = getClaims(email, userType);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(new Date())//
                .setExpiration(new Date(System.currentTimeMillis() + validateMilliseconds))//
                .signWith(getKeyForSign())//
                .compact();
    }

    private String refreshToken() {
        long refreshTime = 3600;
        return Jwts.builder()//
                .setIssuedAt(new Date())//
                .setExpiration(new Date(System.currentTimeMillis() + validateMilliseconds + refreshTime))//
                .signWith(getKeyForSign())//
                .compact();
    }

    private Key getKeyForSign() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> resolveToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("refreshToken");
        if (accessToken != null && refreshToken != null) {
            if (refreshToken.startsWith("Bearer") && accessToken.startsWith("Bearer")) {
                return validateRefreshToken(accessToken.substring(7), refreshToken.substring(7));
            }
        } else if (accessToken != null) {
            if (accessToken.startsWith("Bearer")) {
                List<String> tokens = new ArrayList<>();
                tokens.add(accessToken.substring(7));
                return tokens;
            }
        }
        return null;
    }

    private List<String> validateRefreshToken(String accessToken, String refreshToken) {
        String email = getEmailFromExpiredToken(accessToken);
        User userByEmail = userService.findByEmail(email);
        if (userByEmail.getRefreshToken().equals(refreshToken)) {
            try {
                Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(refreshToken);
            } catch (ExpiredJwtException e) {
                return createTokens(email, userByEmail.getUserType());
            }
            List<String> tokens = new ArrayList<>();
            tokens.add(accessToken(email, userByEmail.getUserType()));
            tokens.add(refreshToken);
            return tokens;
        }
        throw new RuntimeException(" Refresh Token is not correct");
    }

    private String getEmailFromExpiredToken(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = token.split("\\.");
        String email = new String(decoder.decode(parts[1]));
        String[] split = email.split(",");
        return split[0].substring(8,split[0].length()-1);
    }

    public boolean validateToken(List<String> tokens) {
        if (tokens != null) {
            String token = tokens.get(0);
            try {
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
                if (claims.getBody().getExpiration().before(new Date())) {
                    return false;
                }
                return true;
            } catch (ExpiredJwtException e) {
                throw new RuntimeException("Your token is expired");
            }
        }
        return false;

    }
//
//    public List<String> resolveRefreshToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("RefreshToken");
//        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
//            return validateRefreshToken(bearerToken.substring(7));
//        }
//        throw new RuntimeException("RefreshToken has a problem");
//    }
//
//    private List<String> validateRefreshToken(String refreshToken) {
//        try {
//            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(refreshToken);
//            if (claims.getBody().getExpiration().before(new Date())) {
//                return null;
//            }
//            return checkRefreshToken(refreshToken);
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("Your tokens are expired");
//        }
//    }
//
//    public List<String> checkRefreshToken(String refreshToken) {
//        String email = getEmail(refreshToken);
//        User user = userService.findByEmail(email);
//        if (refreshToken.equals(user.getRefreshToken())) {
//            return createTokens(email, user.getUserType());
//        } else {
//            throw new RuntimeException("RefreshToken is not correct");
//        }
//    }
}