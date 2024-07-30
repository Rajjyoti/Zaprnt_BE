package com.zaprent.repo.jwt;

import com.zaprnt.beans.models.JwtData;

public interface CustomJwtRepository {
    void saveJwtData(JwtData data);
    JwtData getJwtDataByToken(String token);
}
