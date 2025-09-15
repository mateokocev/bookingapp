package hr.pocetnik.bookingapp.controller;

import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.service.JwtService;
import hr.pocetnik.bookingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, String> userMap) {

        String name = userMap.get("name");
        String surname = userMap.get("lastname");
        String email = userMap.get("email");
        String password = userMap.get("password");

        Objects.requireNonNull(name, "First name must not be null");
        Objects.requireNonNull(surname, "Last name must not be null");
        Objects.requireNonNull(email, "Email must not be null");
        Objects.requireNonNull(password, "Password must not be null");

        UserEntity user = userService.registerUser(name, surname, email, password);

        String token = jwtService.generateToken(user);
        Map<String, String> tokenMap = Map.of("token", token);

        return new ResponseEntity<>(tokenMap, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> userMap) {

        String email = userMap.get("email");
        String password = userMap.get("password");

        Objects.requireNonNull(email, "Email must not be null");
        Objects.requireNonNull(password, "Password must not be null");

        UserEntity user = userService.loginUser(email, password);

        String token = jwtService.generateToken(user);
        Map<String, String> tokenMap = Map.of("token", token);

        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }
}