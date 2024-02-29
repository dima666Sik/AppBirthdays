package ua.birthdays.app.dao.exceptions;

public class DBConnectionException extends DatabaseException {
    public DBConnectionException(String message) {
        super(message);
    }

    public DBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBConnectionException(Throwable cause) {
        super(cause);
    }

    public DBConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
