package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.util.pool.ConnectionPool;

import java.sql.Connection;

/**
 * Utility "wrapper" class for connecting to the database that interaction with {@link ConnectionPool}
 */
public final class DBConnector {

    private DBConnector() {
    }

    /**
     * Gets a database connection.
     *
     * @return Connection object to connect to the database.
     */
    public static Connection getConnection() {
        return ConnectionPool.takeConnection();
    }

    public static void closePool() {
        ConnectionPool.closePool();
    }
}
