package hr.pocetnik.bookingapp.service;

import hr.pocetnik.bookingapp.model.UserEntity;

public interface JwtService {
    String generateToken(UserEntity user);
}
