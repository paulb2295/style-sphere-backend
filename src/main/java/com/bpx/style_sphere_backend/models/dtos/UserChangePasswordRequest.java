package com.bpx.style_sphere_backend.models.dtos;


public class UserChangePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public static class Builder {
        private String currentPassword;
        private String newPassword;
        private String confirmationPassword;

        public Builder currentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public Builder confirmationPassword(String confirmationPassword) {
            this.confirmationPassword = confirmationPassword;
            return this;
        }

        public UserChangePasswordRequest build() {
            UserChangePasswordRequest request = new UserChangePasswordRequest();
            request.currentPassword = this.currentPassword;
            request.newPassword = this.newPassword;
            request.confirmationPassword = this.confirmationPassword;
            return request;
        }
    }
}
