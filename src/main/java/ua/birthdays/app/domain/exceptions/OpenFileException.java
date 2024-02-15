package ua.birthdays.app.domain.exceptions;

public class OpenFileException extends Exception {
    public OpenFileException() {
    }

    public OpenFileException(String message) {
        super(message);
    }

    public OpenFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenFileException(Throwable cause) {
        super(cause);
    }
}
