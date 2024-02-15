package ua.birthdays.app.dao.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.DBConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for connecting to the database.
 */
public final class DBConnector {
    private static final Logger logger = LogManager.getLogger(DBConnector.class.getName());
    private static final String FILE_PROP_DB_NAME = "db.properties";

    private DBConnector() {
    }

    /**
     * Gets a database connection.
     *
     * @return Connection object to connect to the database.
     */
    public static Connection getConnection() {
        try {
            PropertiesFile prop = new PropertiesFile();
            prop.load(FILE_PROP_DB_NAME);
            logger.info("Properties file was found!");

            Connection connection = DriverManager.getConnection(prop.getProperty("app.birthdays.data.db.url"),
                    prop.getProperty("app.birthdays.data.user.name"), prop.getProperty("app.birthdays.data.password")
            );
            logger.info("Connect to database was successful!");
            return connection;
        } catch (SQLException e) {
            logger.error(e);
            throw new DBConnectionException("Connect to database not successful!", e);
        }
    }
}
