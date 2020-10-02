package com.spk.web.myfirstws.exceptions;

public class UserServiceException extends RuntimeException {


    private static final long serialVersionUID = 6365255169637861295L;

    public UserServiceException(String message) {
        super(message);
    }
}
