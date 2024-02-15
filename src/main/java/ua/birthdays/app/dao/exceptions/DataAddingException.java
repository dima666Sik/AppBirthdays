package ua.birthdays.app.dao.exceptions;

public class DataAddingException extends RuntimeException {
    public DataAddingException() {
    }

    public DataAddingException(String message) {
        super(message);
    }

    public DataAddingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAddingException(Throwable cause) {
        super(cause);
    }
}
