package ua.birthdays.app.dao.exceptions;

public class DataReadingException extends RuntimeException {
    public DataReadingException() {
    }

    public DataReadingException(String message) {
        super(message);
    }

    public DataReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataReadingException(Throwable cause) {
        super(cause);
    }
}
