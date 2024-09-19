package ru.clevertec.app.servlet.filter.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ru.clevertec.app.servlet.filter.model.User;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class TokenService {
    public static final String ROLE = "ROLE";
    private static final long EXPIRATION_TIME = TimeUnit.SECONDS.toMillis(30);
    private static final String SECRET_KEY = "Almmmgf8,FDDFDDFDFd0MEN245dsds9CXOzz9432wqas";

    public String generateTokenByUser(User user) {
        return Jwts.builder()
                .claim(ROLE, user.role())
                .subject(user.username())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public Optional<String> getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Optional.ofNullable(claims.get(TokenService.ROLE, String.class));
    }
}
