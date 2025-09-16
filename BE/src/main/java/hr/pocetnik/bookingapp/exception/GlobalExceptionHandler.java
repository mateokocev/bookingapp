package hr.pocetnik.bookingapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

//  Implementacija po inspiraciji iz DOC: https://www.baeldung.com/spring-boot-return-errors-problemdetail

@RestControllerAdvice  // Omogućava globalno presretanje svih iznimki za sve @RestController klase unutar aplikacije DOC: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestControllerAdvice.html
public class GlobalExceptionHandler {

    //  401 - Invalid Credentials || Kriva lozinka, nemoguće za mail jer će prije toga provjeriti ako user postoji.
    @ExceptionHandler(InvalidCredentialException.class)  //  Omogućava poziv metode kada se bilo gdje u kontrolerima pojavi sljedeća iznimka DOC: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html
    public ProblemDetail handleInvalidCredentials(InvalidCredentialException ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

        pd.setTitle("Invalid credentials");
        pd.setDetail(ex.getMessage());
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    //  400 - Bad Request || Krivi format podataka, ili neispunjeni podaci
    @ExceptionHandler(InvalidEmailFormatException.class)
    public ProblemDetail handleInvalidEmailFormat(InvalidEmailFormatException ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Invalid email format");
        pd.setDetail(ex.getMessage());
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    //  409 - User already exists || Korisnik postoji, konflikt u bazi podataka
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        pd.setTitle("User already exists");
        pd.setDetail(ex.getMessage());
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    //  500 - Fallback for unexpected errors || Za neočekivane greške ili placeholder za nedefinirane u kodu
    @ExceptionHandler(GenericException.class)
    public ProblemDetail handleGenericException(GenericException ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pd.setTitle("Internal server error");
        pd.setDetail(ex.getMessage());
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }
}