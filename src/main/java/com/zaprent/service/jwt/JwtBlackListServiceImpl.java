package com.zaprent.service.jwt;

import com.zaprent.repo.jwt.IJwtRepo;
import com.zaprnt.beans.models.JwtData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class JwtBlackListServiceImpl implements IJwtBlackListService {
    private final IJwtRepo jwtRepo;
    @Override
    public void blackListJwtToken(String token) {
        JwtData data = new JwtData().setJwt(token).setBlackListed(true);
        jwtRepo.saveJwtData(data);
    }

    @Override
    public boolean isTokenBlackListed(String token) {
        JwtData data = jwtRepo.getJwtDataByToken(token);
        return !isNull(data) && data.isBlackListed();
    }
}
