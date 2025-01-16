package com.bpx.style_sphere_backend.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class UserAuthRequest {

    @NotBlank @Email
    private String email;
    @NotBlank
    String password;

    public UserAuthRequest() {
    }

    public UserAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder {
        private String email;
        private String password;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public UserAuthRequest build() {
            UserAuthRequest request = new UserAuthRequest();
            request.email = this.email;
            request.password = this.password;
            return request;
        }
    }
}
