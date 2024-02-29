package ua.birthdays.app.dao.exceptions;

public class DataAddingException extends DatabaseException {
    public DataAddingException(String message) {
        super(message);
    }

    public DataAddingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAddingException(Throwable cause) {
        super(cause);
    }

    public DataAddingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
