package hr.pocetnik.bookingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "billing_data")
public class BillingDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bd_id;

    private String phoneNumber;
    private String address;
    private String postalCode;
    private String city;
    private String region;
    private String country;

    @Column(name = "stripe_customer_id", unique = true)
    private String stripeCustomerId;

    @Column(name = "stripe_account_id", unique = true)
    private String stripeAccountId;

    @ManyToOne(fetch = FetchType.LAZY)  // Definira vrstu odnosa između entiteta. Detektira na koju se tablicu veže gledajući strukturu varijable Doc: https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/manytoone
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id", nullable = false)  // Stvara novi stupac u tablici za FK. Referencira na koji PK se veže te definira da ovaj stupac nikad nesmije biti NULL. Doc: https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/joincolumn
    private UserEntity user;
}