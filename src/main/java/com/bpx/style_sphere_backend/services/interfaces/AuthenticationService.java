package com.bpx.style_sphere_backend.services.interfaces;

import com.bpx.style_sphere_backend.models.dtos.UserAuthRequest;
import com.bpx.style_sphere_backend.models.dtos.UserAuthResponse;
import com.bpx.style_sphere_backend.models.dtos.UserRegisterRequest;
import com.bpx.style_sphere_backend.models.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    UserAuthResponse register(UserRegisterRequest request);

    UserAuthResponse authenticate(UserAuthRequest request);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
