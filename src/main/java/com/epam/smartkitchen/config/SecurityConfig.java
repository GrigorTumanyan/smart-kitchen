package com.epam.smartkitchen.config;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/**";
    private static final String MANAGER_ENDPOINT = "/api/v1/manager/**";
    private static final String CATEGORY_ENDPOINT = "/api/v1/category/**";
    private static final String PRODUCT_ENDPOINT = "/api/v1/product/**";
    private static final String MENU_ENDPOINT = "/api/v1//menu/**";

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(MANAGER_ENDPOINT, CATEGORY_ENDPOINT, PRODUCT_ENDPOINT).hasAuthority(UserType.MANAGER.name())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
