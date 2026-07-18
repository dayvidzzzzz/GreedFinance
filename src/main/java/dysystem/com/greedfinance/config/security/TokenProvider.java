package dysystem.com.greedfinance.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider {

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Value("${jwt.key}")
    private String key;

    public String buildToken(String login, String tenant_id){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);

        Map<String, String> claims = new HashMap<>();
        claims.put("tenant_id", tenant_id);

        return Jwts.builder()
                .subject(login)
                .claims(claims)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isValid(String token){
        try{
            extractClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public String extractTenant(String token) {
        Claims claims = extractClaims(token);
        return claims.get("tenant", String.class);
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}