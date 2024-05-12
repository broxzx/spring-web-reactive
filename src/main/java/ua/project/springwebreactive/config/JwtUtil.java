package ua.project.springwebreactive.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Duration expiration;

    public String generateToken(UserDetails userDetails) {
        Map<String, List<String>> claims = new HashMap<>();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("roles", roles);

        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + expiration.toMillis());

        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(issuedAt)
                .expiration(expiredAt)
                .signWith(getSecretKey())
                .compact();
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String authToken) {
        return getAllClaims(authToken)
                .getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaims(token)
                .get("roles", List.class);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String authToken) {
        return getAllClaims(authToken)
                .getExpiration()
                .before(new Date());
    }
}
