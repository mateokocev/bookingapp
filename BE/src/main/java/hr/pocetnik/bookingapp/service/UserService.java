package hr.pocetnik.bookingapp.service;


import hr.pocetnik.bookingapp.model.UserEntity;

public interface UserService {

    UserEntity  registerUser(String name, String surname, String email, String password);
}
