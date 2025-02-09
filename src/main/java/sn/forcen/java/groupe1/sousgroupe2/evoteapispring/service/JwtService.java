package sn.forcen.java.groupe1.sousgroupe2.evoteapispring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.Jwt;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.model.User;
import sn.forcen.java.groupe1.sousgroupe2.evoteapispring.repository.JwtRepository;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "c8a0b76e4f7cc30bfebf6055310b96a3d8756d4e2284f8518e655b1b0b2cc500";
    private final UserDetailsService userDetailsService;
    private final JwtRepository jwtRepository;

    // Générer un JWT avec une expiration de 1 heure
    public String generateToken(String username) {
        long expirationTime = System.currentTimeMillis() + (1000 * 60 * 60);

        final String javaWebToken = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(expirationTime))
                .signWith(this.getSigningKey())
                .compact();

        Jwt jwtBD = Jwt.builder()
                .disable(false)
                .expire(false)
                .user((User) this.userDetailsService.loadUserByUsername(username))
                .token(javaWebToken)
                .build();

        this.jwtRepository.saveAndFlush(jwtBD);

        return javaWebToken;
    }

    public Jwt tokenBytoken(String token) {
        return this.jwtRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));
    }

    // Extraire le nom d'utilisateur du token
    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    // Vérifier si le token est valide
    public boolean validateToken(String token, String username) {
        return username.equals(this.extractUsername(token)) && !this.isTokenExpired(token);
    }

    public void disconnection() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Jwt> jwtList = this.jwtRepository.findByDisableAndExpireAndUser_Email(false, false, user.getEmail());
         jwtList.forEach(jwt -> {
             jwt.setDisable(true);
             jwt.setExpire(true);
         });
        this.jwtRepository.saveAllAndFlush(jwtList);
    }

    @Scheduled(cron = "0 */60 * * * *")
    @Transactional
    public void removeUseLessJwt() {
        this.jwtRepository.deleteAllByDisableAndExpire(true, true);
        log.info("Token deletion at {}", Instant.now());
    }

    // Récuperation de la clé secrète sécurisée pour signer les JWT
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
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
