package hr.pocetnik.bookingapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("SELLER")
@Getter
@Setter
public class SellerEntity extends UserEntity {

    private String companyName;
    private String companyAddress;
    private String vatNumber;
}