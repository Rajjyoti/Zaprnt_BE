package com.zaprent.config;

import com.zaprent.service.jwt.IJwtBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import static com.zaprent.utils.JWTUtils.getTokenFromCookies;

@Component
public class ApiInterceptor implements HandlerInterceptor {

    private static final String SIGNOUT_ENDPOINT = "/api/auth/signout";

    @Autowired
    private IJwtBlackListService jwtBlacklistService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith(SIGNOUT_ENDPOINT)) {
            String accessToken = getTokenFromCookies(request, "accessToken");
            String refreshToken = getTokenFromCookies(request, "refreshToken");
            if (accessToken != null && refreshToken != null) {
                handleSignOut(accessToken, refreshToken, response);
            }
        }
    }

    private void handleSignOut(String accessToken, String refreshToken, HttpServletResponse response) {
        jwtBlacklistService.blackListJwtToken(accessToken);
        jwtBlacklistService.blackListJwtToken(refreshToken);
        response.addHeader("Set-Cookie", "accessToken=; HttpOnly; Secure; Path=/; SameSite=Strict; Max-Age=0");
        response.addHeader("Set-Cookie", "refreshToken=; HttpOnly; Secure; Path=/; SameSite=Strict; Max-Age=0");
    }
}

