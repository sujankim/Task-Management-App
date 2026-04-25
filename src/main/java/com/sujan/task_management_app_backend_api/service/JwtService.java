package com.sujan.task_management_app_backend_api.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    // ─── TOKEN GENERATION ──────────────────────────────────────────

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        // Add the user's role as a custom claim
        extraClaims.put("role", userDetails.getAuthorities()
                .iterator().next().getAuthority());

        return buildToken(extraClaims, userDetails);
    }

    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)                          // custom claims first
                .subject(userDetails.getUsername())           // subclaim
                .issuedAt(new Date(System.currentTimeMillis()))    // iat
                .expiration(new Date(System.currentTimeMillis() + expirationMs)) // exp
                .signWith(getSigningKey())                    // sign with secret
                .compact();                                   // build the string
    }

    // ─── TOKEN VALIDATION ──────────────────────────────────────────

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ─── CLAIM EXTRACTION ──────────────────────────────────────────

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic claim extractor — pass any function to pull any claim
    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())   // verify signature
                .build()
                .parseSignedClaims(token)      // parse + validate
                .getPayload();                 // return claims
        // Throws ExpiredJwtException if expired
        // Throws MalformedJwtException if token is invalid
        // Throws SignatureException if signature doesn't match
    }

    // ─── KEY HELPER ────────────────────────────────────────────────

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}