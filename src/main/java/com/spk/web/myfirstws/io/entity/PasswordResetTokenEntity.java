package com.spk.web.myfirstws.io.entity;

public class PasswordResetTokenEntity {
    private String token;
    private UserEntity userDetails;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
