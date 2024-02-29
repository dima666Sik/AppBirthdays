package ua.birthdays.app.dao.exceptions;

public class TableExistsException extends DatabaseException {

    public TableExistsException(String message) {
        super(message);
    }

    public TableExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableExistsException(Throwable cause) {
        super(cause);
    }

    public TableExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
