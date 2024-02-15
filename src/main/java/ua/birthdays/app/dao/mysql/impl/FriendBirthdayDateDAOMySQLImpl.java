package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.FriendBirthdayDateDAO;
import ua.birthdays.app.dao.exceptions.DataAddingException;
import ua.birthdays.app.dao.query.QueryFriendBirthdayDate;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.models.FriendBirthdayDate;

import java.sql.*;

/**
 * Implementation of the {@link FriendBirthdayDateDAO} interface.
 * This class provides methods to interact with the 'friend_birthday_date' table.
 * It includes functionality to create new records in the table.
 *
 * @author leniv
 * @version 1.0
 * @see FriendBirthdayDateDAO
 * @see FriendBirthdayDate
 * @see UtilDAO
 * @see DBConnector
 * @see QueryFriendBirthdayDate
 */
public class FriendBirthdayDateDAOMySQLImpl implements FriendBirthdayDateDAO {
    private static final Logger logger = LogManager.getLogger(FriendBirthdayDateDAOMySQLImpl.class.getName());

    /**
     * Creates a new record in the 'friend_birthday_date' table.
     *
     * @param friendBirthdayDate the data of the new record
     * @return the generated record ID, or -1 if the operation fails
     * @throws DataAddingException if an error occurs while adding the data
     */
    @Override
    public long createFriendBirthdayDate(final FriendBirthdayDate friendBirthdayDate) {
        UtilDAO.isTableFriendBirthdayDateExist();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryFriendBirthdayDate.INSERT_INTO_FRIEND_BIRTHDAY_DATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(friendBirthdayDate.getFriendDate()));
            statement.setInt(2, friendBirthdayDate.getRemindedFriendHour());
            statement.setInt(3, friendBirthdayDate.getRemindedFriendMinutes());
            statement.setString(4, friendBirthdayDate.getPeriodTimeEnum()
                                                     .name());
            statement.setInt(5, friendBirthdayDate.getRemindedCountDaysBeforeBirthday());
            if (statement.executeUpdate() > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        logger.info("Create FriendBirthdayDate was successful!");
                        return generatedKeys.getLong(1);
                    } else {
                        logger.info("Key of new row is not found!");
                        return -1;
                    }
                }
            } else {
                logger.info("Row is not added!");
                return -1;
            }
        } catch (SQLException e) {
            logger.error("Cannot create FriendBirthdayDate!", e);
            throw new DataAddingException("Cannot create FriendBirthdayDate!", e);
        }
    }


}
