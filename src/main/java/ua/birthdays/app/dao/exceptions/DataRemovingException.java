package ua.birthdays.app.dao.exceptions;

public class DataRemovingException extends DatabaseException {
    public DataRemovingException(String message) {
        super(message);
    }

    public DataRemovingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataRemovingException(Throwable cause) {
        super(cause);
    }

    public DataRemovingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
