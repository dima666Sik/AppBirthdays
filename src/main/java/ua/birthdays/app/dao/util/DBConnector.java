package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for connecting to the database.
 */
public class DBConnector {
    private final static Logger logger = LogManager.getLogger(DBConnector.class.getName());

    private final static String FILE_PROP_DB_NAME = "db.properties";

    /**
     * Gets a database connection.
     *
     * @return Connection object to connect to the database.
     * @throws DAOException If an error occurred while connecting to the database.
     */
    public static Connection getConnection() throws DAOException {
        try {
            PropertiesFile prop = new PropertiesFile();
            prop.load(FILE_PROP_DB_NAME);
            logger.info("Properties file was found!");

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(prop.getProperty("app.birthdays.data.db.url"),
                    prop.getProperty("app.birthdays.data.user.name"), prop.getProperty("app.birthdays.data.password")
            );
            logger.info("Connect to database was successful!");
            return connection;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("Connect to database not successful!", e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
            throw new DAOException("Failed to load JDBC driver.", e);
        }
    }
}
