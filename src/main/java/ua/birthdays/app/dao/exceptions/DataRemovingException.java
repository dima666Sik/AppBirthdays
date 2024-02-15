package ua.birthdays.app.dao.exceptions;

public class DataRemovingException extends RuntimeException {
    public DataRemovingException() {
    }

    public DataRemovingException(String message) {
        super(message);
    }

    public DataRemovingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataRemovingException(Throwable cause) {
        super(cause);
    }
}
