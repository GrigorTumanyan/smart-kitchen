package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.AuthenticationRequestDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.exceptions.ConflictException;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.models.User;

import com.epam.smartkitchen.repository.UserRepository;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.security.jwt.JwtTokenProvider;
import com.epam.smartkitchen.service.AuthService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public Response<ErrorResponse, UserDto> register(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ConflictException("Email : " + userDto.getEmail() + " already exists");
        }
        User user = UserDto.toUser(userDto);
        String s = generateRandomPassword();
        user.setPassword(bCryptPasswordEncoder.encode(s));
        System.out.println(s);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = UserDto.toUserDto(savedUser);
//        todo send mailing
        return new Response<>(null, savedUserDto, UserDto.class.getSimpleName());
    }


    @Override
    public Response<ErrorResponse, UserDto> login(AuthenticationRequestDto requestDto, HttpServletResponse response) {

        String email = requestDto.getEmail();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Email : " + email + " is not found"));
        generateTokens(response, email, user);
        UserDto userDto = UserDto.toUserDto(user);
        return new Response<>(null, userDto, null);

    }

    @Override
    public void updateRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        if (response != null) {
            String email = jwtTokenProvider.getEmail(response.getHeader("accessToken"));
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Email : " + email + "is not found"));
            String refreshToken = response.getHeader("refreshToken");
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
        throw new RuntimeException("Refresh Token is not correct ");
    }

    private void generateTokens(HttpServletResponse response, String email, User user) {
        List<String> tokens = jwtTokenProvider.createTokens(email, user.getUserType());
        user.setRefreshToken(tokens.get(1));
        userRepository.save(user);
        response.setHeader("access_token", tokens.get(0));
        response.setHeader("refresh_token", tokens.get(1));
    }

    private String generateRandomPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(3, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(3);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }
}
