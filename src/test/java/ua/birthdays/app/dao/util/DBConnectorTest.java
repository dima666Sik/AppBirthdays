package ua.birthdays.app.dao.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectorTest {

    private Connection connection;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            connection = DBConnector.getConnection();
        });
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void getConnection() {
        assertNotNull(connection);
    }
}