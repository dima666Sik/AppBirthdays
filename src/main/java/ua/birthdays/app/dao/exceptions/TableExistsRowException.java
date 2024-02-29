package ua.birthdays.app.dao.exceptions;

public class TableExistsRowException extends DatabaseException {

    public TableExistsRowException(String message) {
        super(message);
    }

    public TableExistsRowException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableExistsRowException(Throwable cause) {
        super(cause);
    }

    public TableExistsRowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
