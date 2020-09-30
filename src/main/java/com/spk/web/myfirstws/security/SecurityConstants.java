package com.spk.web.myfirstws.security;

import com.spk.web.myfirstws.SpringApplicationContext;

public class SecurityConstants {

    //Token gonna be valid for 10 days
    public static final long EXPIRATION_TIME = 864000000; //This value in ms.

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String SIGN_UP_URL = "/users";

    //This will be used in the encryption of value of the access token
    //public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }


}
