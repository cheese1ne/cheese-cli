package com.cheese.boot.core.secure.exception;

/**
 * token错误异常
 *
 * @author sobann
 */
public class TokenErrorException extends RuntimeException {

    public TokenErrorException() {
        super("token error");
    }
}
