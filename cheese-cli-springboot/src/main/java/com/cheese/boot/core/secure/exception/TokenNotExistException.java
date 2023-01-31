package com.cheese.boot.core.secure.exception;

/**
 * token不存在
 *
 * @author sobann
 */
public class TokenNotExistException extends RuntimeException {
    public TokenNotExistException() {
        super("token not exist");
    }
}
