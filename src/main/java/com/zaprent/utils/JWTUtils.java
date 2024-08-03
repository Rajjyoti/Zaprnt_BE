package com.zaprent.utils;

import com.zaprnt.beans.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class JWTUtils {
    private static final String JWT_SECRET = "60c59e553be577cae67382cdee56d89b8b41a847cdf7db9357cdfddb6d5ef4e3"; // Replace with a strong secret
    public static final long ACCESS_TOKEN_EXPIRY = 1000 * 60 * 30;
    public static final long REFRESH_TOKEN_EXPIRY = 14 * 24 * 60 * 60 * 1000;
    public final static  int MAX_COOKIE_AGE_IN_SECONDS = 15 * 24 * 60 * 60;

    public static String generateAccessToken(String userName, String email, List<UserType> roles) {
        return generateToken(userName, email, roles, ACCESS_TOKEN_EXPIRY);
    }

    public static String generateRefreshToken(String userName, String email, List<UserType> roles) {
        return generateToken(userName, email, roles, REFRESH_TOKEN_EXPIRY);
    }

    public static HttpHeaders getResponseHeadersForAuth(String userName, String email, List<UserType> roles) {
        String accessToken = JWTUtils.generateAccessToken(userName, email, roles);
        String refreshToken = JWTUtils.generateRefreshToken(userName, email, roles);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "accessToken=" + accessToken + "; HttpOnly; Secure; Path=/; SameSite=Strict; Max-Age=" + MAX_COOKIE_AGE_IN_SECONDS);
        headers.add("Set-Cookie", "refreshToken=" + refreshToken + "; HttpOnly; Secure; Path=/; SameSite=Strict; Max-Age=" + MAX_COOKIE_AGE_IN_SECONDS);
        return headers;
    }

    public static String generateToken(String userName, String email, List<UserType> roles, long expiryKey) {
        Map<String, Object> claims = getJWTClaims(userName, email, roles);
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiryKey))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static String getTokenFromCookies(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }

    private static Map<String, Object> getJWTClaims(String userName, String email, List<UserType> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);
        claims.put("email", email);
        claims.put("roles", roles);
        return claims;
    }
}
