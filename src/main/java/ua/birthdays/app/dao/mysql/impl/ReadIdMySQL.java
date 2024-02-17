package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.DataReadingException;
import ua.birthdays.app.dao.query.QueryUser;
import ua.birthdays.app.dao.query.QueryUserFriendsData;
import ua.birthdays.app.dao.util.DBConnector;

import java.sql.*;

/**
 * The ReadIdMySQL class provides methods for reading user and friends data IDs from the MySQL database.
 * It includes functionality to check the existence of a user, retrieve the user's ID based on email and password,
 * and retrieve the friends data ID based on user ID, friend's name, and friend's birthday date.
 * <p>
 * This class is designed as a utility class with static methods and cannot be instantiated.
 *
 * @see DBConnector
 * @see QueryUser
 * @see QueryUserFriendsData
 */
public class ReadIdMySQL {
    private ReadIdMySQL() {
    }

    private static final Logger logger = LogManager.getLogger(ReadIdMySQL.class);

    public static long readIdUser(final String email, final String password) {
        long id = 0;
        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUser.FIND_USER_BY_EMAIL_AND_PASSWORD)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);

            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("id_user");
            }
            if (id == 0) {
                logger.error("User is not found. Check table in db!");
            }
            logger.info("Check id user exist successful");

        } catch (Exception e) {
            logger.error(e);
        }
        return id;
    }

    public static long readIdFriendsDataRowByIdUserAndFriendNameAndDateFriendBirthday(long idUser, String friendsName, String dateFriendBirthday) {
        long idFriendsData = 0;
        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUserFriendsData.EXISTS_BY_USER_ID_AND_FRIEND_NAME_AND_FRIEND_DATE)) {
            statement.setLong(1, idUser);
            statement.setString(2, friendsName);
            statement.setDate(3, Date.valueOf(dateFriendBirthday));

            var resultSet = statement.executeQuery();
            if (resultSet.next()) idFriendsData = resultSet.getLong("id_user_friends_data");
        } catch (SQLException e) {
            logger.error("Cannot check ufd row exists!", e);
            throw new DataReadingException("Cannot check ufd row exists!", e);
        }
        return idFriendsData;
    }
}