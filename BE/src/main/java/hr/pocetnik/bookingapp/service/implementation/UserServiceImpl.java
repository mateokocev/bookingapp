package hr.pocetnik.bookingapp.service.implementation;

import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.repository.UserRepository;
import hr.pocetnik.bookingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public UserEntity registerUser(String name, String surname, String email, String password) {

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String modifiedEmail = email.toLowerCase(Locale.ROOT);

        try {
            if (!EMAIL_PATTERN.matcher(modifiedEmail).matches()) {
                throw new RuntimeException("Invalid email format");
            }

            if (userRepository.existsByEmail(modifiedEmail)) {
                throw new RuntimeException("User already exists");
            }

            // 1. Create a new UserEntity instance
            UserEntity newUser = new UserEntity();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setEmail(modifiedEmail);
            newUser.setPassword(hashedPassword);

            // 2. Save it using the repository. Spring Data JPA handles the rest!
            return userRepository.save(newUser);


        } catch (RuntimeException e) {
            throw new RuntimeException("Error when creating user: " + e.getMessage(), e);
        }
    }

}

