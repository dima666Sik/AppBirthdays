package ua.birthdays.app.dao.util.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.DBConnectionException;
import ua.birthdays.app.dao.util.Constant;
import ua.birthdays.app.dao.util.PropertiesFile;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static ua.birthdays.app.dao.util.Constant.*;

/**
 * A class that manages a pool of connections to a database.
 */
public final class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class.getName());
    private static BlockingQueue<Connection> pool;
    private static final List<Connection> connections = new ArrayList<>();

    private ConnectionPool() {
    }

    static {
        initConnectionPool();
    }

    /**
     * Initializes the connection pool.
     */
    private static void initConnectionPool() {
        PropertiesFile.load(Constant.DB_NAME_PROP_FILE_KEY);
        var originPoolSize = PropertiesFile.getProperty(Constant.COUNT_CONN_POOL_KEY);

        int poolSize = originPoolSize == null ? Constant.DEFAULT_COUNT_CONN_POOL : Integer.parseInt(originPoolSize);

        pool = new ArrayBlockingQueue<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            Connection connection = getConnection();
            var proxyConnection
                    = (Connection) Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) ->
                            method.getName()
                                  .equals("close")
                                    ? pool.add((Connection) proxy)
                                    : method.invoke(connection, args)
            );
            pool.add(proxyConnection);
            connections.add(connection);
        }
    }

    /**
     * Retrieves a connection from the pool.
     *
     * @return a connection
     * @throws DBConnectionException if there is an error retrieving the connection
     */
    public static Connection takeConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {

            logger.log(Level.WARN, "Interrupted!", e);
            Thread.currentThread()
                  .interrupt();

            throw new DBConnectionException(e);
        }
    }

    /**
     * Closes the connection pool.
     */
    public static void closePool() {
        for (var conn : connections) {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new DBConnectionException(e);
            }
        }
    }

    /**
     * Gets the size of the connection pool.
     *
     * @return the size of the connection pool
     */
    public static int getPoolSize() {
        return connections.size();
    }

    /**
     * Gets a connection to the database.
     *
     * @return a connection to the database
     * @throws DBConnectionException if there is an error getting the connection
     */
    private static Connection getConnection() {
        try {
            PropertiesFile.load(DB_NAME_PROP_FILE_KEY);
            logger.info("Properties file was found!");

            var connection = DriverManager.getConnection(PropertiesFile.getProperty(APP_BIRTHDAYS_DATA_DB_URL),
                    PropertiesFile.getProperty(APP_BIRTHDAYS_DATA_USER_NAME), PropertiesFile.getProperty(APP_BIRTHDAYS_DATA_PASSWORD)
            );
            logger.info("Connect to database was successful!");
            return connection;
        } catch (SQLException e) {
            logger.error(e);
            throw new DBConnectionException("Connect to database not successful!", e);
        }
    }
}
