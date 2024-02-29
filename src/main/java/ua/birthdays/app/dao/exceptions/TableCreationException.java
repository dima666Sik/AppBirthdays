package ua.birthdays.app.dao.exceptions;

public class TableCreationException extends DatabaseException {

    public TableCreationException(String message) {
        super(message);
    }

    public TableCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableCreationException(Throwable cause) {
        super(cause);
    }

    public TableCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
