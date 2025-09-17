package hr.pocetnik.bookingapp.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Authentication token not found or is empty.");
    }
}
