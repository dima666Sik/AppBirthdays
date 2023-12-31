package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.FriendBirthdayDateDAO;
import ua.birthdays.app.dao.query.QueryFriendBirthdayDate;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.models.FriendBirthdayDate;

import java.sql.*;

public class FriendBirthdayDateDAOMySQLImpl implements FriendBirthdayDateDAO {
    private final static Logger logger = LogManager.getLogger(FriendBirthdayDateDAOMySQLImpl.class.getName());

    @Override
    public long createFriendBirthdayDate(final FriendBirthdayDate friendBirthdayDate) throws DAOException {
        UtilDAO.isTableFriendBirthdayDateExist();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryFriendBirthdayDate.createFriendBirthdayDate(), Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(friendBirthdayDate.getFriendDate()));
            statement.setInt(2, friendBirthdayDate.getRemindedFriendHour());
            statement.setInt(3, friendBirthdayDate.getRemindedFriendMinutes());
            statement.setString(4, friendBirthdayDate.getPeriodTimeEnum().name());
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
            throw new DAOException("Cannot create FriendBirthdayDate!", e);
        }
    }


}
