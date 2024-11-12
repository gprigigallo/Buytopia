package com.apuliadigitalmaker.buytopia.common;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // ApiKey 256 bit
    private final String secretKey = "MXl0RBQn73NC4nNiDo6rMR4DgMNT7fx33EJVyNR0Fw0YiMp3WJVxpJGli4xVMsJfcDq4eylyxiQynnaNeYc1kt9SdkyMVHAafNBR6cH8GbsNhuoztvmZ58bwa3FZT3Z7L8huoViCbCV1jKkdQnbVpkkGLvrW5LovdZgv5EC2Ktqc4wKc0QLFhHT4u7WgxyzfjIk15aNUMy57fCXYpsjk9LwTERP7hzUFcBMVKfXk6VtXdEa7fxOKie5QvfIGtojU";
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String username) {
        long expirationTime = 3600000;
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.before(new Date());
    }




}
