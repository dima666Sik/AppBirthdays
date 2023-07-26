package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.env.EnumDBNameTables;
import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.UserFriendsDataDAO;
import ua.birthdays.app.dao.query.QueryAboutFriend;
import ua.birthdays.app.dao.query.QueryFriendBirthdayDate;
import ua.birthdays.app.dao.query.QueryUserFriendsData;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFriendsDataDAOMySQLImpl implements UserFriendsDataDAO {
    private final static Logger logger = LogManager.getLogger(UserFriendsDataDAOMySQLImpl.class.getName());

    @Override
    public boolean createUserFriendsData(UserFriendsData userFriendsData, long idAboutFriendRow, long idFriendBirthdayDateRow) throws DAOException {
        if (!UtilDAO.isTableExists(EnumDBNameTables.USER_FRIENDS_DATA_TABLE.getEnumDBEnvironment())) {
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.createTableUserFriendsData())
            ) {
                statement.executeUpdate();
                logger.info("Create table \"user_friends_data\" was successful!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.createUserFriendsData())) {
            statement.setLong(1, idAboutFriendRow);
            statement.setLong(2, idFriendBirthdayDateRow);
            statement.setLong(3,
                    ReadIdMySQL.readIdUser(userFriendsData.getUser().getEmail(), userFriendsData.getUser().getPassword()));
            statement.executeUpdate();
            logger.info("Create createUserFriendsData was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create createUserFriendsData!", e);
            throw new DAOException("Cannot create createUserFriendsData!", e);
        }
        return true;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsData(User user) throws DAOException {
        List<UserFriendsData> userFriendsDataList = new ArrayList<>();

        if (!UtilDAO.isTableExists(EnumDBNameTables.USER_FRIENDS_DATA_TABLE.getEnumDBEnvironment())) {
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.createTableUserFriendsData())
            ) {
                statement.executeUpdate();
                logger.info("Create table \"user_friends_data\" was successful!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserId())) {
            statement.setInt(1, ReadIdMySQL.readIdUser(user.getEmail(), user.getPassword()));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userFriendsDataList.add(
                            new UserFriendsData(
                                    new FriendBirthdayDate(
                                            resultSet.getDate("friend_date").toLocalDate(),
                                            resultSet.getInt("reminded_friend_hour"),
                                            resultSet.getInt("reminded_friend_minutes"),
                                            resultSet.getInt("reminded_count_days_before_birthday")
                                    ),
                                    new AboutFriend(resultSet.getString("name_friend")),
                                    user
                            )
                    );
                }
                logger.info("Read ALL User Friends Data was successful!");
            }
        } catch (SQLException e) {
            logger.error("Cannot create user!", e);
            throw new DAOException("Cannot create user!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public boolean updateUserFriendsData(int idUserFriendsData) throws DAOException {
        return false;
    }

    @Override
    public boolean deleteUserFriendsData(int idUserFriendsData) throws DAOException {
        return false;
    }
}
