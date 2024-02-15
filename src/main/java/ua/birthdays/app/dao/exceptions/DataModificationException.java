package ua.birthdays.app.dao.exceptions;

public class DataModificationException extends RuntimeException {
    public DataModificationException() {
    }

    public DataModificationException(String message) {
        super(message);
    }

    public DataModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataModificationException(Throwable cause) {
        super(cause);
    }
}
