package ua.birthdays.app.dao.exceptions;

public class TableExistsException extends RuntimeException {
    public TableExistsException() {
    }

    public TableExistsException(String message) {
        super(message);
    }

    public TableExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableExistsException(Throwable cause) {
        super(cause);
    }
}
