package hr.pocetnik.bookingapp.repository;


import hr.pocetnik.bookingapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);


}
