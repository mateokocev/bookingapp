package hr.pocetnik.bookingapp.service.implementation;

import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMillis:3600000}")
    private Long expMillis;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("Id", user.getId())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }
}
