package com.dogoo.SystemWeighingSas.validator;

import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import com.dogoo.SystemWeighingSas.unitity.token.ClaimsKeys;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

@Service
public class TokenValidator {

    @Value("${dogoo.jwt.secret.key}")
    public String secretLKey;

    public Response validatorToken(HttpServletRequest request){

        String userContext = request.getHeader(ClaimsKeys.USER_CONTEXT_REFRESH_TOKEN);

        try {
            Jwts.parserBuilder()
                    .setSigningKey(
                            DatatypeConverter
                                    .parseBase64Binary(secretLKey))
                    .build()
                    .parseClaimsJws(userContext).getBody();
        }
        catch (Exception ex){
            return ResponseFactory.getClientErrorResponse(ex.getMessage());
        }

        return null;
    }
}
