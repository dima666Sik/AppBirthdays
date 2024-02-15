package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.AboutFriendDAO;
import ua.birthdays.app.dao.exceptions.DataAddingException;
import ua.birthdays.app.dao.query.QueryAboutFriend;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.models.AboutFriend;

import java.sql.*;

/**
 * Implementation of the {@link AboutFriendDAO} interface.
 * This class provides methods to interact with the "AboutFriend" table in the database,
 * such as creating a new record for a friend's information.
 *
 * @author leniv
 * @version 1.0
 * @see AboutFriendDAO
 * @see UtilDAO
 * @see DBConnector
 * @see QueryAboutFriend
 */
public class AboutFriendDAOMySQLImpl implements AboutFriendDAO {
    private static final Logger logger = LogManager.getLogger(AboutFriendDAOMySQLImpl.class.getName());

    /**
     * Creates a new AboutFriend record in the database.
     *
     * @param aboutFriend the AboutFriend object to be added
     * @return the ID of the newly created record, or -1 if operation failed
     */
    @Override
    public long createAboutFriend(final AboutFriend aboutFriend) {
        UtilDAO.isTableAboutFriendExist();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(QueryAboutFriend.INSERT_INTO_ABOUT_FRIEND, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, aboutFriend.getNameFriend());

            if (statement.executeUpdate() > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        logger.info("Create AboutFriend was successful!");
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
            logger.error("Cannot create AboutFriend!", e);
            throw new DataAddingException("Cannot create AboutFriend!", e);
        }
    }


}
