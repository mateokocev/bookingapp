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

    @ExceptionHandler(InvalidCredentialException.class)  //  Omogućava poziv metode kada se bilo gdje u kontrolerima pojavi sljedeća iznimka DOC: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html
    public ProblemDetail handleInvalidCredentials(InvalidCredentialException ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

        pd.setTitle("Invalid credentials");
        pd.setDetail(ex.getMessage());
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }
}