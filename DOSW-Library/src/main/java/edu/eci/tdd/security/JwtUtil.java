package edu.eci.tdd.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Llave segura: JJWT ahora prefiere que la llave tenga al menos 256 bits
    private final String SECRET_STRING = "esta_es_una_llave_super_secreta_y_larga_para_la_biblioteca_eci_2026";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // 10 horas de validez
    private final int TOKEN_EXPIRATION = 1000 * 60 * 60 * 10;

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username) // Cambio: ya no es setSubject
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // Cambio: ya no es setIssuedAt
                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION)) // Cambio: ya no es setExpiration
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // Cambio: parserBuilder() ahora es parser() y se usa verifyWith y build
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}