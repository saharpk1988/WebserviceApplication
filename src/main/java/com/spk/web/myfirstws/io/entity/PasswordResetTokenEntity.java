package com.spk.web.myfirstws.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

    private static final long serialVersionUID = 7863862991269895321L;
    @Id
    @GeneratedValue
    private long id;

    private String token;

    @OneToOne // One token can be only associated to one user
    @JoinColumn(name = "users_id")
    private UserEntity userDetails; // foreign key of password_reset_tokens table


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
