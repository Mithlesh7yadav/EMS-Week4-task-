package com.UpgradedEMS.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 */
@Component
public class JwtUtil {

    /** Base‑64 encoded secret configured in application.properties */
    @Value("${jwt.secret}")
    private String secret;

    /** Signing key derived from the secret */
    private Key key;

    /** Token lifetime (1 hour) */
    private static final long EXPIRATION_MS = 1_000 * 60 * 60;

    /* ---------------------------------------------------------------
       Initialise the signing key once Spring has injected the secret
       --------------------------------------------------------------- */
    @PostConstruct
    private void init() {
        byte[] bytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /* -------- Generate a token for an authenticated user ---------- */
    public String generateToken(UserDetails user) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles",
                        user.getAuthorities().stream()
                                .map(Object::toString)
                                .toList())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /* --------------- Extract username (= subject) ----------------- */
    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    /* -------- Validate token against supplied user details -------- */
    public boolean isValid(String token, UserDetails user) {
        return user.getUsername().equals(extractUsername(token)) && !isExpired(token);
    }

    /* ----------------- Helper methods ----------------------------- */
    private boolean isExpired(String token) {
        return parse(token).getBody().getExpiration().before(new Date());
    }

    private Jws<Claims> parse(String token) {
        return null;
    }

    public String username(String token) {
        return parse(token).getPayload().getSubject();
    }
}
