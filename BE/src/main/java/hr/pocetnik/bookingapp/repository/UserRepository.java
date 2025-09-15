package hr.pocetnik.bookingapp.repository;


import hr.pocetnik.bookingapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);  // SELECT COUNT(*) > 0 FROM users WHERE email = ?;
    Optional<UserEntity> findByEmail(String email);  // SELECT * FROM users WHERE email = ?;
}