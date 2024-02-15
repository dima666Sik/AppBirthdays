package ua.birthdays.app.dao.exceptions;

public class DBPropertiesFileException extends RuntimeException {
    public DBPropertiesFileException() {
    }

    public DBPropertiesFileException(String message) {
        super(message);
    }

    public DBPropertiesFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBPropertiesFileException(Throwable cause) {
        super(cause);
    }
}
