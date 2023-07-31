package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.env.EnumDBNameTables;
import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.AboutFriendDAO;
import ua.birthdays.app.dao.query.QueryAboutFriend;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.models.AboutFriend;

import java.sql.*;

public class AboutFriendDAOMySQLImpl implements AboutFriendDAO {
    private final static Logger logger = LogManager.getLogger(AboutFriendDAOMySQLImpl.class.getName());

    @Override
    public long createAboutFriend(AboutFriend aboutFriend) throws DAOException {
        if (!UtilDAO.isTableExists(EnumDBNameTables.ABOUT_FRIEND_TABLE.getEnumDBEnvironment())) {
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(QueryAboutFriend.createTableAboutFriend())
            ) {
                statement.executeUpdate();
                logger.info("Create table \"about_friend\" was successful!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (Connection connection = DBConnector.getConnection();

             PreparedStatement statement = connection.prepareStatement(QueryAboutFriend.createAboutFriend(), Statement.RETURN_GENERATED_KEYS)) {
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
            throw new DAOException("Cannot create AboutFriend!", e);
        }
    }


}
