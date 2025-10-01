package hr.pocetnik.bookingapp.repository;

import hr.pocetnik.bookingapp.model.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

    boolean existsByEmail(String email);
}