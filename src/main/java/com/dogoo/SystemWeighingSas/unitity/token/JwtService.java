package com.dogoo.SystemWeighingSas.unitity.token;

public interface JwtService {
    String getJWToken(String userName);
    String getRefreshToken(String userName);
}
