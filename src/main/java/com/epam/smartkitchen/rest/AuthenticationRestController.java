package com.epam.smartkitchen.rest;

import com.epam.smartkitchen.dto.AuthenticationRequestDto;
import com.epam.smartkitchen.dto.UserDto;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.security.jwt.JwtTokenProvider;
import com.epam.smartkitchen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestController(BCryptPasswordEncoder bCryptPasswordEncoder,AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto, HttpServletResponse response) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            User user = userService.findByEmail(email);
            validationTokens(response,email,user);
            return ResponseEntity.ok("You are redirecting the new page soon");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        User user = new UserDto(userDto).toUser();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User registeredUser = userService.register(user);
        if (registeredUser == null) {
            return ResponseEntity.badRequest().body("user is not created");
        }

        return ResponseEntity.ok("User created");
    }

    @PostMapping("refresh")
    public ResponseEntity refresh(HttpServletRequest request, HttpServletResponse response) {
        if (response != null) {
            String email = jwtTokenProvider.getEmail(response.getHeader("accessToken"));
            User user = userService.findByEmail(email);
            String refreshToken = response.getHeader("refreshToken");
            userService.updateRefreshToken(user,refreshToken);
            return ResponseEntity.ok("Your tokens are updated");
        }
        throw new RuntimeException("Refresh Token is not correct ");
    }

    private void validationTokens(HttpServletResponse response,String email, User user){
        List<String> tokens = jwtTokenProvider.createTokens(email, user.getUserType());
        userService.updateRefreshToken(user, tokens.get(1));
        response.setHeader("access_token", tokens.get(0));
        response.setHeader("refresh_token", tokens.get(1));
    }
}
