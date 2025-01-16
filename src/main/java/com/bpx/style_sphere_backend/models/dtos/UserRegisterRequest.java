package com.bpx.style_sphere_backend.models.dtos;

import com.bpx.style_sphere_backend.enums.AppRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class UserRegisterRequest {

    @NotBlank(message = " First Name Mandatory!")
    private String firstname;
    @NotBlank(message = "Last Name Mandatory!")
    private String lastname;
    @NotBlank(message = "Email Mandatory!") @Email(message = "Insert a valid email!")
    private String email;
    @Size(min = 8, message = "Password Must have 8 or more characters!")
    private String password;
    private AppRole appRole;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String lastname, String firstname, String email, String password, AppRole appRole) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.appRole = appRole;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public AppRole getAppRole() {
        return appRole;
    }

    public void setAppRole(AppRole appRole) {
        this.appRole = appRole;
    }

    public static class Builder {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private AppRole appRole;

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder appRole(AppRole appRole) {
            this.appRole = appRole;
            return this;
        }

        public UserRegisterRequest build() {
            UserRegisterRequest request = new UserRegisterRequest();
            request.firstname = this.firstname;
            request.lastname = this.lastname;
            request.email = this.email;
            request.password = this.password;
            request.appRole = this.appRole;
            return request;
        }
    }
}
