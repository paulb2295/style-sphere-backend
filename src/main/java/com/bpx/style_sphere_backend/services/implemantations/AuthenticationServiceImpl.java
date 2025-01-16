package com.bpx.style_sphere_backend.services.implemantations;

import com.bpx.style_sphere_backend.enums.AppRole;
import com.bpx.style_sphere_backend.enums.TokenType;
import com.bpx.style_sphere_backend.models.dtos.UserAuthRequest;
import com.bpx.style_sphere_backend.models.dtos.UserAuthResponse;
import com.bpx.style_sphere_backend.models.dtos.UserRegisterRequest;
import com.bpx.style_sphere_backend.models.entities.Token;
import com.bpx.style_sphere_backend.models.entities.User;
import com.bpx.style_sphere_backend.repositories.UserRepository;
import com.bpx.style_sphere_backend.security.JwtHandler;
import com.bpx.style_sphere_backend.services.interfaces.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bpx.style_sphere_backend.repositories.TokenRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHandler jwtHandler;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtHandler jwtHandler, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtHandler = jwtHandler;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserAuthResponse register(UserRegisterRequest request) {
        var user = new User.Builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .appRole(AppRole.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtHandler.generateToken(extractUserDetails(savedUser),savedUser); //jwtHandler.generateToken(user);
        var refreshToken = jwtHandler.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new UserAuthResponse.Builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserAuthResponse authenticate(UserAuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtHandler.generateToken(extractUserDetails(user),user); //jwtHandler.generateToken(user);
        var refreshToken = jwtHandler.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new UserAuthResponse.Builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = new Token.Builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtHandler.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtHandler.isTokenValid(refreshToken, user)) {
                var accessToken = jwtHandler.generateToken(extractUserDetails(user),user); //jwtHandler.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new UserAuthResponse.Builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    //Add extra claims to JWT
    private Map<String, Object> extractUserDetails(User user) {
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("id", user.getId());
        userClaims.put("email", user.getEmail());
        userClaims.put("firstname", user.getFirstname());
        userClaims.put("lastname", user.getLastname());
        userClaims.put("role", user.getAppRole());
        return userClaims;
    }
}
