package com.dogoo.SystemWeighingSas.common;

import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.unitity.token.ClaimsKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

@Service
public class CommonToken {

    @Value("${dogoo.jwt.secret.key}")
    public String secretLKey;

    public UserTokenModel getUserToken(HttpServletRequest httpServletRequest) {
        String userContext = httpServletRequest.getHeader(ClaimsKeys.USER_CONTEXT);

        Claims claims = decodeJWToken(userContext);

        UserTokenModel userToken = new UserTokenModel();

        userToken.setEmail(claims.get(ClaimsKeys.EMAIL) != null ?
                claims.get(ClaimsKeys.EMAIL).toString() : "");
        userToken.setAccountId(claims.get(ClaimsKeys.ACCOUNT_ID) != null ?
                Long.parseLong(claims.get(ClaimsKeys.ACCOUNT_ID).toString()) : 0L);
        userToken.setKey(claims.get(ClaimsKeys.KEY) != null ?
                claims.get(ClaimsKeys.KEY).toString() : "");
        userToken.setRoles(claims.get(ClaimsKeys.ROLES) != null ?
                claims.get(ClaimsKeys.ROLES).toString() : "");
        userToken.setRoleCreate(claims.get(ClaimsKeys.ROLE_CREATE) != null ?
                Boolean.parseBoolean(claims.get(ClaimsKeys.ROLES).toString()) : Boolean.FALSE);
        userToken.setRoleView(claims.get(ClaimsKeys.ROLE_VIEW) != null ?
                Boolean.parseBoolean(claims.get(ClaimsKeys.ROLES).toString()) : Boolean.FALSE);

        return userToken;
    }

    private Claims decodeJWToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(
                        DatatypeConverter
                                .parseBase64Binary(secretLKey))
                .build()
                .parseClaimsJws(token).getBody();
    }
}
