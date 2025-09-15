package hr.pocetnik.bookingapp.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("Invalid email or password");
    }
}