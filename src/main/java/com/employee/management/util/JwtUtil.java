package com.employee.management.util;

import com.employee.management.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET=
            "this-is-a-very-strong-secret-key-32bytes";

    private final Key key= Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+60*60*1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Boolean isTokenValid(String token){
        try {
                extractClaims(token);
                return true;
        }catch (JwtException e){
            return false;
        }
    }
    public void enforceAdmin(String authHeader){
        if (authHeader==null || !authHeader.startsWith("Bearer")){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "missing or invalid header"
            );
        }
        String token=authHeader.replace("Bearer ","").trim();
        if (!isTokenValid(token)){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "invalid or expired token"
            );
        }
        Claims claims=extractClaims(token);
        Role role=Role.valueOf(claims.get("role",String.class));

        if(role!=Role.ADMIN){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "only admins can perform this operation"
            );
        }
    }
}
