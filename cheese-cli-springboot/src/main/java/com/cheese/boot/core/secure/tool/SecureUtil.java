package com.cheese.boot.core.secure.tool;

import com.cheese.boot.common.constant.NormalCharConstant;
import com.cheese.boot.common.constant.NumberConstant;
import com.cheese.boot.common.tool.EnvironmentHelper;
import com.cheese.boot.core.secure.constant.TokenConstant;
import com.cheese.core.tool.util.Func;
import com.cheese.core.tool.util.ObjectUtil;
import com.cheese.core.tool.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 认证工具类
 *
 * @author sobann
 */
@Component
public class SecureUtil implements InitializingBean {

    private static final String AUTHORITIES_KEY = TokenConstant.AUTHORITIES_KEY;
    private final static String HEADER = TokenConstant.HEADER;
    private final static String BEARER = TokenConstant.BEARER;
    private final static String ACCOUNT = TokenConstant.ACCOUNT;
    private final static String USER_ID = TokenConstant.USER_ID;
    private final static String ROLE_ID = TokenConstant.ROLE_ID;
    private final static String USER_NAME = TokenConstant.USER_NAME;
    private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
    private final static String TENANT_ID = TokenConstant.TENANT_ID;
    private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
    private final static Integer AUTH_LENGTH = TokenConstant.AUTH_LENGTH;
    private static final String BASE64_SECRET = Base64.getEncoder().encodeToString(TokenConstant.SIGN_KEY.getBytes(StandardCharsets.UTF_8));
    private static final Long DEFAULT_TOKEN_EXPIRED = TokenConstant.TOKEN_EXPIRED;

    private static String secret;
    private static Long expired;


    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    public static Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        //获取用户身份信息
        return parseAuthentication(claims);
    }

    public static String createToken(Authentication authentication) {
        /*
         * 获取权限列表
         */
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(NormalCharConstant.COMMA));

        return Jwts.builder()
                .setId(Func.randomUUID())
                //自定义信息
                .claim(AUTHORITIES_KEY, authorities)
                //过期时间
                .setExpiration(createTokenExpireTime())
                //用户名称
                .setSubject(authentication.getName())
                .compact();
    }

    /**
     * 从请求中获取token
     *
     * @param request
     * @return
     */
    public static String obtainToken(HttpServletRequest request) {
        String auth = request.getHeader(SecureUtil.HEADER);
        if (StringUtil.isNotBlank(auth) && auth.length() > AUTH_LENGTH) {
            String headStr = auth.substring(NumberConstant.ZERO, NumberConstant.SIX).toLowerCase();
            if (headStr.compareTo(SecureUtil.BEARER) == NumberConstant.ZERO) {
                auth = auth.substring(NumberConstant.SEVEN);
            }
        } else {
            auth = request.getParameter(SecureUtil.HEADER);
        }
        return auth;
    }

    private static Authentication parseAuthentication(Claims claims) {
        //获取用户身份信息
        Object authoritiesStr = claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities =
                ObjectUtil.isNotEmpty(authoritiesStr) ?
                        Arrays.stream(authoritiesStr.toString().split(NormalCharConstant.COMMA))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()) : Collections.emptyList();
        User principal = new User(claims.getSubject(), NormalCharConstant.EMPTY, authorities);
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    /**
     * 解析jsonWebToken
     *
     * @param request
     * @return Claims
     */
    private static Claims parseClaims(HttpServletRequest request) {
        //从请求中获取token
        String token = obtainToken(request);
        if (Func.isNotEmpty(token)) {
            return parseClaims(token);
        }
        return null;
    }

    /**
     * 解析jsonWebToken
     *
     * @param jsonWebToken jsonWebToken
     * @return Claims
     */
    private static Claims parseClaims(String jsonWebToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJwt(jsonWebToken).getBody();
        } catch (ExpiredJwtException e) {
            //token失效异常
            //throw new TokenExpiredException();
            return null;
        } catch (Exception e) {
            //token错误异常
            //throw new TokenErrorException();
            return null;
        }
    }

    /**
     * base64加密后的签名
     *
     * @return
     */
    private static SecretKey generalKey() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] encodedKey = secret.getBytes();
        SecretKey signingKey = new SecretKeySpec(encodedKey, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    /**
     * token有效时间
     * <p>
     * expired设定为分钟
     *
     * @return
     */
    private static Date createTokenExpireTime() {
        return new Date(System.currentTimeMillis() + expired * NumberConstant.SIXTY * NumberConstant.THOUSAND);
    }

    /**
     * 工具类中提供的所有信息优先以配置信息为准
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (Func.isEmpty(secret)) {
            secret = EnvironmentHelper.getEnvironment().getProperty("jwt.token.base64_key", BASE64_SECRET);
        }
        if (Func.isEmpty(expired)) {
            expired = EnvironmentHelper.getEnvironment().getProperty("jwt.token.expired", Long.class, DEFAULT_TOKEN_EXPIRED);
        }
    }
}
