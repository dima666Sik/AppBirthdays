package ua.birthdays.app.dao.exceptions;

public class TableCreationException extends RuntimeException {
    public TableCreationException() {
    }

    public TableCreationException(String message) {
        super(message);
    }

    public TableCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableCreationException(Throwable cause) {
        super(cause);
    }
}
