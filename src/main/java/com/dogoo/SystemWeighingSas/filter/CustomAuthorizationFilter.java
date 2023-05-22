package com.dogoo.SystemWeighingSas.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dogoo.SystemWeighingSas.unitity.token.ClaimsKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final String key = "ZPv6tAtBdgPjmdU5kg4wNTThZjgx33b7MLgU3QFPNXMzuEdy8t";
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/dogoo/login") ){
            filterChain.doFilter(request,response);
        }else{
            String authorization = request.getHeader(ClaimsKeys.USER_CONTEXT);
            if (authorization != null ){
                try {
                    String token = authorization ;
                    Jwts.parserBuilder().setSigningKey(
                            DatatypeConverter.parseBase64Binary(key))
                            .build().parseClaimsJws(token);

                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(
                                    DatatypeConverter
                                            .parseBase64Binary(key))
                            .build()
                            .parseClaimsJws(token).getBody();
                    String email = claims.get(ClaimsKeys.EMAIL).toString();
                    String role = claims.get(ClaimsKeys.ROLES).toString();
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(role));
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }catch (Exception e){
                    response.setHeader("error" , e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String , String > err = new HashMap<>();
                    err.put("error_messenge" , e.getMessage());
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream() , err);
                }
            }else{
                Map<String , String > err = new HashMap<>();
                err.put("error_messenge" , "Forbidden");
                response.setContentType("application/json");
                filterChain.doFilter(request,response);
            }
        }
    }
}
