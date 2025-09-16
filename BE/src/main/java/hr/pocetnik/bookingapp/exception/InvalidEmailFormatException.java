package hr.pocetnik.bookingapp.exception;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
        super("Invalid email format.");
    }
}
