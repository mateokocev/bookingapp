package hr.pocetnik.bookingapp.service;

import hr.pocetnik.bookingapp.model.UserEntity;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(UserEntity user);
    Boolean verifyToken(String token);
    String extractEmail(String token);
    Claims extractAllClaims(String token);
}