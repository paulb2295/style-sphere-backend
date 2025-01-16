package com.bpx.style_sphere_backend.services.interfaces;

import com.bpx.style_sphere_backend.models.dtos.UserChangePasswordRequest;

import java.security.Principal;

public interface UserService {

    void changePassword(UserChangePasswordRequest request, Principal connectedUser);
}
