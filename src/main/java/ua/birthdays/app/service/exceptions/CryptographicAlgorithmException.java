package ua.birthdays.app.service.exceptions;

public class CryptographicAlgorithmException extends RuntimeException {
    public CryptographicAlgorithmException() {
    }

    public CryptographicAlgorithmException(String message) {
        super(message);
    }

    public CryptographicAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptographicAlgorithmException(Throwable cause) {
        super(cause);
    }
}
