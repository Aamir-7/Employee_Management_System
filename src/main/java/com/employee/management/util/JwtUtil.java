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
import java.util.UUID;

@Component
public class JwtUtil {

    private static final String SECRET =
            "this-is-a-very-strong-secret-key-32bytes";

    private final Key key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    /* ======================
       TOKEN CREATION
       ====================== */
    public String generateToken(UUID employeeId, Role role) {

        return Jwts.builder()
                .setSubject(employeeId.toString())   // UUID stored as String
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 60 * 60 * 1000)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /* ======================
       CLAIM EXTRACTION
       ====================== */
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /* ======================
       TOKEN VALIDATION
       ====================== */
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /* ======================
       ADMIN ONLY
       ====================== */
    public void enforceAdmin(String authHeader) {

        Claims claims = validateAndGetClaims(authHeader);
        Role role = Role.valueOf(claims.get("role", String.class));

        if (role != Role.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only ADMIN can perform this operation"
            );
        }
    }

    /* ======================
       ADMIN OR MANAGER
       ====================== */
    public void enforceAdminOrManager(String authHeader) {

        Claims claims = validateAndGetClaims(authHeader);
        Role role = Role.valueOf(claims.get("role", String.class));

        if (role != Role.ADMIN && role != Role.MANAGER) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only ADMIN or MANAGER can perform this operation"
            );
        }
    }

    /* ======================
       ADMIN OR MANAGER OR Employee itself
       ====================== */
    public void enforceAdminOrSelf(String authHeader, UUID employeeId) {

        Claims claims = extractClaimsFromHeader(authHeader);

        UUID tokenEmployeeId = UUID.fromString(claims.getSubject());
        Role role = Role.valueOf(claims.get("role", String.class));

        if (role == Role.ADMIN) {
            return; // admin allowed
        }

        if (!tokenEmployeeId.equals(employeeId)) {
            throw new RuntimeException("Access denied");
        }
    }


    /* ======================
       COMMON VALIDATION
       ====================== */
    private Claims validateAndGetClaims(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Missing or invalid Authorization header"
            );
        }



        String token = authHeader.replace("Bearer ", "").trim();

        if (!isTokenValid(token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid or expired token"
            );
        }

        return extractClaims(token);
    }

    private Claims extractClaimsFromHeader(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7).trim(); // remove "Bearer "
        return extractClaims(token);
    }

}
