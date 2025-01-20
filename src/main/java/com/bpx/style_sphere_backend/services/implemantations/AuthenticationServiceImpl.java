package com.bpx.style_sphere_backend.services.implemantations;

import com.bpx.style_sphere_backend.enums.AppRole;
import com.bpx.style_sphere_backend.enums.TokenType;
import com.bpx.style_sphere_backend.exceptions.InvalidRefreshTokenException;
import com.bpx.style_sphere_backend.exceptions.RefreshTokenGeneratingException;
import com.bpx.style_sphere_backend.exceptions.UsedEmailException;
import com.bpx.style_sphere_backend.exceptions.WrongEmailPasswordException;
import com.bpx.style_sphere_backend.models.dtos.UserAuthRequest;
import com.bpx.style_sphere_backend.models.dtos.UserAuthResponse;
import com.bpx.style_sphere_backend.models.dtos.UserRegisterRequest;
import com.bpx.style_sphere_backend.models.entities.Token;
import com.bpx.style_sphere_backend.models.entities.User;
import com.bpx.style_sphere_backend.repositories.UserRepository;
import com.bpx.style_sphere_backend.security.JwtHandler;
import com.bpx.style_sphere_backend.services.interfaces.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bpx.style_sphere_backend.repositories.TokenRepository;

import java.io.IOException;
import java.util.Arrays;
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
    public UserAuthResponse register(UserRegisterRequest request, HttpServletResponse response) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new UsedEmailException("Email already associated to an account!");
        }
        var user = new User.Builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .appRole(AppRole.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtHandler.generateToken(extractUserDetails(savedUser), savedUser); //jwtHandler.generateToken(user);
        var refreshToken = jwtHandler.generateRefreshToken(user);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(24*60*60);
        refreshCookie.setDomain("localhost");
        response.addCookie(refreshCookie);

        saveUserToken(savedUser, jwtToken);
        return new UserAuthResponse.Builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public UserAuthResponse authenticate(UserAuthRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException authenticationException) {
            throw new WrongEmailPasswordException("Wrong Email or Password!");
        }

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new WrongEmailPasswordException("Wrong Email or Password!"));

        var jwtToken = jwtHandler.generateToken(extractUserDetails(user), user); //jwtHandler.generateToken(user);
        var refreshToken = jwtHandler.generateRefreshToken(user);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(24*60*60);
        refreshCookie.setDomain("localhost");
        response.addCookie(refreshCookie);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new UserAuthResponse.Builder()
                .accessToken(jwtToken)
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

        if(request.getCookies() == null){
            throw new RefreshTokenGeneratingException("No cookies sent!");
        }
        final String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        if(refreshToken == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new RefreshTokenGeneratingException("No refresh token sent!");
        }

        String userEmail = jwtHandler.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow(() -> new InvalidRefreshTokenException("Could not re-authenticate user. Sign In!"));
            if (jwtHandler.isTokenValid(refreshToken, user)) {
                var accessToken = jwtHandler.generateToken(extractUserDetails(user), user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new UserAuthResponse.Builder()
                        .accessToken(accessToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }else {
                throw new InvalidRefreshTokenException("Invalid refresh token!");
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
