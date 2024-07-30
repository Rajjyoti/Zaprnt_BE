package com.zaprent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaprent.service.jwt.IJwtBlackListService;
import com.zaprent.utils.JWTUtils;
import com.zaprnt.beans.enums.UserType;
import com.zaprnt.beans.error.ErrorDetails;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static com.zaprent.utils.JWTUtils.MAX_COOKIE_AGE_IN_SECONDS;
import static com.zaprent.utils.JWTUtils.getTokenFromCookies;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.errorResponseEntity;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final String SIGNIN_ENDPOINT = "/api/auth/signin";
    private static final String SIGNUP_ENDPOINT = "/api/auth/signup";
    private static final String GET_PRODUCT_BY_ID_ENDPOINT = "/api/products/id/";
    private static final String GET_PRODUCTS_BY_CATEGORY_ENDPOINT = "/api/products/categories";
    private static final String GET_REVIEWS_BY_PRODUCT_ID = "/api/reviews/product-ids";
    private final List<String> EXCLUDED_PATHS = Arrays.asList(SIGNUP_ENDPOINT, SIGNIN_ENDPOINT, GET_PRODUCT_BY_ID_ENDPOINT, GET_PRODUCTS_BY_CATEGORY_ENDPOINT, GET_REVIEWS_BY_PRODUCT_ID);

    @Autowired
    private IJwtBlackListService jwtBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestURI = request.getRequestURI();

        // Exclude signup and signin paths
        if (EXCLUDED_PATHS.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getTokenFromCookies(request, "accessToken");
        String refreshToken = getTokenFromCookies(request, "refreshToken");

        if (accessToken != null && !jwtBlacklistService.isTokenBlackListed(accessToken) && JWTUtils.validateToken(accessToken)) {
            filterChain.doFilter(request, response);
        } else if (refreshToken != null && !jwtBlacklistService.isTokenBlackListed(refreshToken) && JWTUtils.validateToken(refreshToken)) {
            Claims claims = JWTUtils.parseClaims(refreshToken);
            String username = claims.get("userName", String.class);
            String email = claims.get("email", String.class);
            Set<UserType> roles = claims.get("roles", Set.class);
            accessToken = JWTUtils.generateAccessToken(username, email, roles);

            // Set the new access token as a cookie in the response header
            response.addHeader(HttpHeaders.SET_COOKIE, "accessToken=" + accessToken + "; HttpOnly; Secure; Path=/; SameSite=Strict; Max-Age=" + MAX_COOKIE_AGE_IN_SECONDS);
            filterChain.doFilter(request, response);
        } else {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorDetails errorDetail = new ErrorDetails("Unauthorized: Invalid or expired tokens");
            String jsonResponse = new ObjectMapper().writeValueAsString(errorResponseEntity(errorDetail, HttpStatus.UNAUTHORIZED).getBody());
            response.getWriter().write(jsonResponse);
        }
    }
}

