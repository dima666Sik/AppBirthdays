package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.env.EnumDBNameTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.TableCreationException;
import ua.birthdays.app.dao.exceptions.TableExistsException;
import ua.birthdays.app.dao.exceptions.TableExistsRowException;
import ua.birthdays.app.dao.query.QueryAboutFriend;
import ua.birthdays.app.dao.query.QueryFriendBirthdayDate;
import ua.birthdays.app.dao.query.QueryUser;
import ua.birthdays.app.dao.query.QueryUserFriendsData;

import java.sql.*;
import java.time.LocalDate;

import static ua.birthdays.app.dao.util.Constant.*;

/**
 * Utility class for working with the database.
 */
public final class UtilDAO {
    private static final Logger logger = LogManager.getLogger(UtilDAO.class.getName());

    private UtilDAO() {
    }

    /**
     * Checks whether a table with the given name exists in the database.
     *
     * @param nameTable the name of the table to check
     * @return true if the table exists; false if the table does not exist
     */
    public static boolean isTableExists(String nameTable) {

        PropertiesFile.load(DB_NAME_PROP_FILE_KEY);
        logger.info("Properties file was found!");

        try (var connection = DBConnector.getConnection();
             var tables = connection.getMetaData()
                                    .getTables(
                                            PropertiesFile.getProperty(APP_BIRTHDAYS_DATA_DB_NAME),
                                            null,
                                            nameTable,
                                            new String[]{"TABLE"})
        ) {

            return tables.next();
        } catch (SQLException e) {
            logger.error("Cannot check table is exists!", e);
            throw new TableExistsException("Cannot check table is exists!", e);
        }
    }

    public static void isUserTableExists() {
        if (!UtilDAO.isTableExists(EnumDBNameTable.USER_TABLE.getEnumDBEnvironment())) {
            try (var connection = DBConnector.getConnection();
                 var statement = connection.prepareStatement(QueryUser.CREATE_TABLE_USER)
            ) {
                statement.executeUpdate();
                logger.info("Create table \"users\" was successful!");
            } catch (SQLException e) {
                logger.info("Error table \"users\" create!", e);
                throw new TableCreationException("Error table \"users\" create!", e);
            }
        }
    }

    /**
     * Checks if a friend record exists with the specified parameters.
     *
     * @param idUser             the user ID
     * @param friendsName        friend's name
     * @param dateFriendBirthday date of friend's birthday
     * @return true if the record exists; false if the record does not exist
     */
    public static boolean isUserFriendsDataRowExists(long idUser,
                                                     String friendsName,
                                                     LocalDate dateFriendBirthday) {
        try (var connection = DBConnector.getConnection();
             var statement
                     = connection.prepareStatement(QueryUserFriendsData.EXISTS_BY_USER_ID_AND_FRIEND_NAME_AND_FRIEND_DATE)) {
            statement.setLong(1, idUser);
            statement.setString(2, friendsName);
            statement.setDate(3, Date.valueOf(dateFriendBirthday));
            return statement.executeQuery()
                            .next();
        } catch (SQLException e) {
            logger.error("Cannot check ufd row exists!", e);
            throw new TableExistsRowException("Cannot check ufd row exists!", e);
        }
    }

    public static void isTableUserFriendDataExist() {
        if (!UtilDAO.isTableExists(EnumDBNameTable.USER_FRIENDS_DATA_TABLE.getEnumDBEnvironment())) {
            try (var connection = DBConnector.getConnection();
                 var statement = connection.prepareStatement(QueryUserFriendsData.CREATE_TABLE_USER_FRIENDS_DATA)
            ) {
                statement.executeUpdate();
                logger.info("Create table \"user_friends_data\" was successful!");
            } catch (SQLException e) {
                logger.info("Error table \"user_friends_data\" create!", e);
                throw new TableCreationException("Error table \"user_friends_data\" create!", e);
            }
        }
    }

    public static void isTableFriendBirthdayDateExist() {
        if (!UtilDAO.isTableExists(EnumDBNameTable.FRIEND_BIRTHDAY_DATE_TABLE.getEnumDBEnvironment())) {
            try (var connection = DBConnector.getConnection();
                 var statement = connection.prepareStatement(QueryFriendBirthdayDate.CREATE_TABLE_FRIEND_BIRTHDAY_DATE)
            ) {
                statement.executeUpdate();
                logger.info("Create table \"friend_birthday_date\" was successful!");
            } catch (SQLException e) {
                logger.info("Error table \"friend_birthday_date\" create!", e);
                throw new TableCreationException("Error table \"friend_birthday_date\" create!", e);
            }
        }
    }

    public static void isTableAboutFriendExist() {
        if (!UtilDAO.isTableExists(EnumDBNameTable.ABOUT_FRIEND_TABLE.getEnumDBEnvironment())) {
            try (var connection = DBConnector.getConnection();
                 var statement = connection.prepareStatement(QueryAboutFriend.CREATE_TABLE_ABOUT_FRIEND)
            ) {
                statement.executeUpdate();
                logger.info("Create table \"about_friend\" was successful!");
            } catch (SQLException e) {
                logger.info("Error table \"about_friend\" create!", e);
                throw new TableCreationException("Error table \"about_friend\" create!", e);
            }
        }
    }

}
