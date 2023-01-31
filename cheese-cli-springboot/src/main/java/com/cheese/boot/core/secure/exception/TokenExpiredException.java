package com.cheese.boot.core.secure.exception;

/**
 * token失效异常
 *
 * @author sobann
 */
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("token expired");
    }
}
