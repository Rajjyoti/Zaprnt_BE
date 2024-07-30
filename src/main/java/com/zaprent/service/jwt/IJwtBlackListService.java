package com.zaprent.service.jwt;

public interface IJwtBlackListService {
    //TODO: will use redis later
    void blackListJwtToken(String token);
    boolean isTokenBlackListed(String token);
}
