package hr.pocetnik.bookingapp.service;

import hr.pocetnik.bookingapp.model.UserEntity;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(UserEntity user);
    Claims extractAllClaims(String token);
}