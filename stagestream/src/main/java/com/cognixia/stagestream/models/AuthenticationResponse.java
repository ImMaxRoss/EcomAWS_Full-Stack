package com.cognixia.stagestream.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    
    @JsonProperty("token")
    private String token;

    @JsonProperty("userId")
    private Long userId;


    public AuthenticationResponse() { }

    public AuthenticationResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }


    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}