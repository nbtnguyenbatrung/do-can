package com.dogoo.SystemWeighingSas.unitity.token;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.service.AccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private AccountService accountService;

    @Value("${dogoo.jwt.secret.key}")
    public String secretLKey;

    @Value("${dogoo.jwt.expiration.ms}")
    private Integer expirationTime;

    @Value("${dogoo.jwt.expiration.refresh.ms}")
    private Integer expirationRefreshTime;

    @Value("${dogoo.subject}")
    private String subject;

    @Override
    public String getJWToken(String userName) {

        Account account = accountService.getAccountByScreenName(userName);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        String token;

        byte[] apiKeySecretBytes =
                DatatypeConverter.parseBase64Binary(secretLKey);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, expirationTime);
        Claims claims = Jwts.claims().setSubject(subject);


        claims.put(ClaimsKeys.ACCOUNT_ID, account.getAccountId());
        claims.put(ClaimsKeys.EMAIL, account.getEmail());
        claims.put(ClaimsKeys.KEY,
                account.getKey() != null ? account.getKey() : "" );
        claims.put(ClaimsKeys.ROLES,
                account.getRole() != null ? account.getRole() : "");
        claims.put(ClaimsKeys.ROLE_CREATE,
                account.getRoleCreate() != null ? account.getRoleCreate() : Boolean.FALSE);
        claims.put(ClaimsKeys.ROLE_VIEW,
                account.getRoleView() != null ? account.getRoleView() : Boolean.FALSE);
        claims.put(ClaimsKeys.SCREEN_NAME, account.getScreenName());
        claims.put(ClaimsKeys.NAME, account.getName());

        token  = Jwts.builder()
                .setClaims(claims)
                .setExpiration(c.getTime())
                .signWith(signingKey,signatureAlgorithm)
                .compact();

        return token;
    }

    @Override
    public String getRefreshToken(String userName) {

        Account account = accountService.getAccountByScreenName(userName);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        String token;

        byte[] apiKeySecretBytes =
                DatatypeConverter.parseBase64Binary(secretLKey);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, expirationRefreshTime);
        Claims claims = Jwts.claims().setSubject(subject);


        claims.put(ClaimsKeys.ACCOUNT_ID, account.getAccountId());
        claims.put(ClaimsKeys.EMAIL, account.getEmail());
        claims.put(ClaimsKeys.KEY,
                account.getKey() != null ? account.getKey() : "" );
        claims.put(ClaimsKeys.ROLES,
                account.getRole() != null ? account.getRole() : "");
        claims.put(ClaimsKeys.ROLE_CREATE,
                account.getRoleCreate() != null ? account.getRoleCreate() : Boolean.FALSE);
        claims.put(ClaimsKeys.ROLE_VIEW,
                account.getRoleView() != null ? account.getRoleView() : Boolean.FALSE);
        claims.put(ClaimsKeys.SCREEN_NAME, account.getScreenName());
        claims.put(ClaimsKeys.NAME, account.getName());

        token  = Jwts.builder()
                .setClaims(claims)
                .setExpiration(c.getTime())
                .signWith(signingKey,signatureAlgorithm)
                .compact();

        return token;

    }
}
