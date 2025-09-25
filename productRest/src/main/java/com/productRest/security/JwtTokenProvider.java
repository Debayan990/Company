package com.productRest.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.security.secret}")
    private String secret;

    @Value("${app.security.expiration}")
    private long expiryDate;

    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        Date currentDate=new Date();

        Date expiry=new Date(currentDate.getTime()+expiryDate);

        String token= Jwts.builder().subject(username)
                .expiration(expiry)
                .issuedAt(new Date())
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getUsername(String token){
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
        return true;
    }
}
