package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "c8a0b76e4f7cc30bfebf6055310b96a3d8756d4e2284f8518e655b1b0b2cc500";

    // Générer un JWT avec une expiration de 1 heure
    public String generateToken(String username) {
        long expirationTime = System.currentTimeMillis() + (1000 * 60 * 60);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(expirationTime))
                .signWith(this.getSigningKey())
                .compact();
    }

    // Récuperation de la clé secrète sécurisée pour signer les JWT
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extraire le nom d'utilisateur du token
    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    // Vérifier si le token est valide
    public boolean validateToken(String token, String username) {
        return username.equals(this.extractUsername(token)) && !this.isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return this.extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build();
        return parser.parseSignedClaims(token).getPayload();
    }
}
