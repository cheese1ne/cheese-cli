package com.cheese.boot.core.secure.constant;

/**
 * @author sobann
 */
public interface TokenConstant {
    String SIGN_KEY = "cheeseclisupportsecuritydemo";
    String AVATAR = "avatar";
    String HEADER = "token";
    String BEARER = "bearer";
    String ACCESS_TOKEN = "access_token";
    String REFRESH_TOKEN = "refresh_token";
    String TOKEN_TYPE = "token_type";
    String EXPIRES_IN = "expires_in";
    String ACCOUNT = "account";
    String USER_ID = "user_id";
    String ROLE_ID = "role_id";
    String DEPT_ID = "dept_id";
    String USER_NAME = "user_name";
    String ROLE_NAME = "role_name";
    String TENANT_ID = "tenant_id";
    String OAUTH_ID = "oauth_id";
    String CLIENT_ID = "client_id";
    Integer AUTH_LENGTH = 7;
    String AUTHORITIES_KEY = "auth";
    Long TOKEN_EXPIRED = 180L;
}
