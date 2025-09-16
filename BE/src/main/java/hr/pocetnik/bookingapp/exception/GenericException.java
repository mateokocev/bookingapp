package hr.pocetnik.bookingapp.exception;

public class GenericException extends RuntimeException {
    public GenericException(String message) {
        super("Internal error: " + message);
    }
}