package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.mysql.impl.UserDAOMySQLImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.query.QueryUserFriendsData;

import java.sql.*;
import java.time.LocalDate;

public class UtilDAO {
    private final static Logger logger = LogManager.getLogger(UserDAOMySQLImpl.class.getName());

    private final static String FILE_PROP_DB_NAME = "db.properties";

    public static boolean isTableExists(String nameTable) throws DAOException {

        PropertiesFile propertiesFile = new PropertiesFile();
        propertiesFile.load(FILE_PROP_DB_NAME);
        logger.info("Properties file was found!");

        try (Connection connection = DBConnector.getConnection();
             ResultSet tables = connection.getMetaData().getTables(propertiesFile.getProperty("app.birthdays.data.db.name"), null, nameTable, new String[]{"TABLE"})
        ) {

            return tables.next();
        } catch (SQLException e) {
            logger.error("Cannot check table is exists!", e);
            throw new DAOException("Cannot check table is exists!", e);
        }
    }

    public static boolean isUserFriendsDataRowExists(long idUser, String friendsName, LocalDate dateFriendBirthday) throws DAOException {
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.existsByUserIdAndFriendNameAndFriendDate())){
            statement.setLong(1,idUser);
            statement.setString(2,friendsName);
            statement.setDate(3, Date.valueOf(dateFriendBirthday));
            return statement.executeQuery().next();
        } catch (SQLException e) {
            logger.error("Cannot check ufd row exists!", e);
            throw new DAOException("Cannot check ufd row exists!", e);
        }
    }
}
