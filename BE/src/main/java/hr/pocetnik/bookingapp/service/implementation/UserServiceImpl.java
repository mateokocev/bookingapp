package hr.pocetnik.bookingapp.service.implementation;

import hr.pocetnik.bookingapp.exception.InvalidCredentialException;
import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.repository.UserRepository;
import hr.pocetnik.bookingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static String SQL_CREATE = "INSERT INTO USER (NAME, SURNAME, EMAIL, PASSWORD) " + "VALUES(NEXTVAL('USERS_SEQ'), ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


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

            final KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, surname);
                ps.setString(3, modifiedEmail);
                ps.setString(4, hashedPassword);
                return ps;
            }, keyHolder);

            Long userId = keyHolder.getKey().longValue();

            return userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException(
                            "Could not find user after creation with id: " + userId));

        } catch (RuntimeException e) {
            throw new RuntimeException("Error when creating user: " + e.getMessage());
        }
    }

    // PODSJETNIK DODAJ SVE IZNIMKE I REFORMATIRAJ KOD DA BUDE PO GLOBAL HANDLER STANDARDU
    public UserEntity loginUser(String email, String password) {

        String modifiedEmail = email.toLowerCase(Locale.ROOT);

        try {

            if (!EMAIL_PATTERN.matcher(modifiedEmail).matches()) {
                throw new RuntimeException("Invalid email format");
            }

            UserEntity user = userRepository.findByEmail(modifiedEmail)
                    .orElseThrow(() -> new InvalidCredentialException());

            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new InvalidCredentialException();
            }

            return user;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Error when authenticating user: " + e.getMessage());
        }
    }
}

