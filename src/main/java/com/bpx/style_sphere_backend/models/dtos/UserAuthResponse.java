package com.bpx.style_sphere_backend.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAuthResponse {

    @JsonProperty("access_token")
    private String accessToken;


    public UserAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserAuthResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public static class Builder {
        private String accessToken;


        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }


        public UserAuthResponse build() {
            UserAuthResponse response = new UserAuthResponse();
            response.accessToken = this.accessToken;
            return response;
        }
    }
}
