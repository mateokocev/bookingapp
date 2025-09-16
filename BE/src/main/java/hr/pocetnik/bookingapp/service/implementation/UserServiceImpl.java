package hr.pocetnik.bookingapp.service.implementation;

import hr.pocetnik.bookingapp.exception.InvalidCredentialException;
import hr.pocetnik.bookingapp.exception.InvalidEmailFormatException;
import hr.pocetnik.bookingapp.exception.UserAlreadyExistsException;
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


        if (!EMAIL_PATTERN.matcher(modifiedEmail).matches()) {
            throw new InvalidEmailFormatException();
        }

        if (userRepository.existsByEmail(modifiedEmail)) {
            throw new UserAlreadyExistsException(email);
        }

        UserEntity newUser = new UserEntity();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setEmail(modifiedEmail);
        newUser.setPassword(hashedPassword);

        return userRepository.save(newUser);
    }

    @Override
    public UserEntity loginUser(String email, String password) {

        String modifiedEmail = email.toLowerCase(Locale.ROOT);

        if (!EMAIL_PATTERN.matcher(modifiedEmail).matches()) {
            throw new InvalidEmailFormatException();
        }

        UserEntity user = userRepository.findByEmail(modifiedEmail)
                .orElseThrow(() -> new InvalidCredentialException());  // Može biti referenca na konstruktor, ali je nepotrebno. Ovako je čitljivije

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new InvalidCredentialException();
        }

        return user;
    }
}

