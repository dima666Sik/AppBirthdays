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
import ua.birthdays.app.models.UserFriendsData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserFriendsDataDAOMySQLImpl implements UserFriendsDataDAO {
    private final static Logger logger = LogManager.getLogger(UserFriendsDataDAOMySQLImpl.class.getName());

    @Override
    public boolean createUserFriendsData(UserFriendsData userFriendsData) throws DAOException {
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

        if (!UtilDAO.isTableExists(EnumDBNameTables.FRIEND_BIRTHDAY_DATE_TABLE.getEnumDBEnvironment())) {
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(QueryFriendBirthdayDate.createTableFriendBirthdayDate())
            ) {
                statement.executeUpdate();
                logger.info("Create table \"friend_birthday_date\" was successful!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.createUserFriendsData())) {
            statement.setInt(1, ReadIdMySQL.readIdAboutFriend(userFriendsData.getAboutFriend()));
            statement.setInt(2, ReadIdMySQL.readIdFriendBirthdayDate(userFriendsData.getFriendBirthdayDate()));
            statement.setInt(3,
                    ReadIdMySQL.readIdUser(userFriendsData.getUser().getEmail(), userFriendsData.getUser().getPassword()));
            statement.executeUpdate();
            logger.info("Create user was successful!");
            statement.executeUpdate();
        }catch (SQLException e){
            logger.error("Cannot create user!", e);
            throw new DAOException("Cannot create user!", e);
        }
        return true;
    }

    @Override
    public UserFriendsData readUserFriendsData(int idUserFriendsData) throws DAOException {
        UserFriendsData userFriendsData = null;

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

        if (!UtilDAO.isTableExists(EnumDBNameTables.FRIEND_BIRTHDAY_DATE_TABLE.getEnumDBEnvironment())) {
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(QueryFriendBirthdayDate.createTableFriendBirthdayDate())
            ) {
                statement.executeUpdate();
                logger.info("Create table \"friend_birthday_date\" was successful!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try(Connection connection = DBConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.createUserFriendsData())) {
            statement.setInt(1, ReadIdMySQL.readIdAboutFriend(userFriendsData.getAboutFriend()));

            statement.executeUpdate();
            logger.info("Create user was successful!");
            statement.executeUpdate();
        }catch (SQLException e){
            logger.error("Cannot create user!", e);
            throw new DAOException("Cannot create user!", e);
        }
        return userFriendsData;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsData(int idUser) throws DAOException {
        return null;
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
