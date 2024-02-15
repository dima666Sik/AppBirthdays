package ua.birthdays.app.dao.exceptions;

public class TableExistsRowException extends RuntimeException {
    public TableExistsRowException() {
    }

    public TableExistsRowException(String message) {
        super(message);
    }

    public TableExistsRowException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableExistsRowException(Throwable cause) {
        super(cause);
    }
}
