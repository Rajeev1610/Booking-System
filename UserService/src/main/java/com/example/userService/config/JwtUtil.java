package com.example.userService.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY= "a6038ca3-5dd0-4ead-83ad-3ca9fdb56d28";
    private static final long EXPIRATION_TIME = 3600000;
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String userName){
        return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String validateToken(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(key)
                    .build().parseClaimsJws(token).getBody()
                    .getSubject();
        }catch (JwtException e){
            throw new IllegalStateException("Invalid JWT token");
        }
    }
}
