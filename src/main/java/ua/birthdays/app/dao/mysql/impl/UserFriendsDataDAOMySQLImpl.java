package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.UserFriendsDataDAO;
import ua.birthdays.app.dao.query.QueryUserFriendsData;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserFriendsDataDAOMySQLImpl implements UserFriendsDataDAO {
    private final static Logger logger = LogManager.getLogger(UserFriendsDataDAOMySQLImpl.class.getName());
    private List<UserFriendsData> userFriendsDataList;

    @Override
    public boolean createUserFriendsData(final UserFriendsData userFriendsData, final long idAboutFriendRow, final long idFriendBirthdayDateRow) throws DAOException {
        long idUser = ReadIdMySQL.readIdUser(userFriendsData.getUser().getEmail(), userFriendsData.getUser().getPassword());

        UtilDAO.isTableUserFriendDataExist();

        if (UtilDAO.isUserFriendsDataRowExists(idUser, userFriendsData.getAboutFriend().getNameFriend(), userFriendsData.getFriendBirthdayDate().getFriendDate())) {
            logger.warn("You have the same info into table about ufd!");
            return false;
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.createUserFriendsData())) {
            statement.setLong(1, idAboutFriendRow);
            statement.setLong(2, idFriendBirthdayDateRow);
            statement.setLong(3, idUser);
            statement.executeUpdate();
            logger.info("Create createUserFriendsData was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create createUserFriendsData!", e);
            throw new DAOException("Cannot create createUserFriendsData!", e);
        }
        return true;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDefault(final User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        UtilDAO.isTableAboutFriendExist();

        UtilDAO.isTableFriendBirthdayDateExist();

        UtilDAO.isTableUserFriendDataExist();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserId())) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(final User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdDescendingFriendBirthdayDate())) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(final User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdAscendingFriendBirthdayDate())) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(final User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdDescendingNameFriend())) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    private void readByIdUserUserFriendData(User user, PreparedStatement statement) throws SQLException {
        statement.setLong(1, ReadIdMySQL.readIdUser(user.getEmail(), user.getPassword()));
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                userFriendsDataList.add(
                        new UserFriendsData(
                                new FriendBirthdayDate(
                                        resultSet.getDate("friend_date").toLocalDate(),
                                        resultSet.getInt("reminded_friend_hour"),
                                        resultSet.getInt("reminded_friend_minutes"),
                                        PeriodTimeEnum.valueOf(resultSet.getString("reminded_period_time_enum")),
                                        resultSet.getInt("reminded_count_days_before_birthday")
                                ),
                                new AboutFriend(resultSet.getString("name_friend")),
                                user
                        )
                );
            }
            logger.info("Read ALL User Friends Data was successful!");
        }
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(final User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdAscendingNameFriend())) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public boolean updateUserFriendsData(final UserFriendsData oldUserFriendsData, final UserFriendsData newUserFriendsData) throws DAOException {
        long idUser = ReadIdMySQL.readIdUser(newUserFriendsData.getUser().getEmail(), newUserFriendsData.getUser().getPassword());

        if (UtilDAO.isUserFriendsDataRowExists(idUser, newUserFriendsData.getAboutFriend().getNameFriend(), newUserFriendsData.getFriendBirthdayDate().getFriendDate())) {
            logger.warn("You have the same info into table about ufd!");
            return false;
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.updateUserFriendsDataByIdUserFriendDate())) {
            statement.setDate(1, Date.valueOf(newUserFriendsData.getFriendBirthdayDate().getFriendDate()));
            statement.setInt(2, newUserFriendsData.getFriendBirthdayDate().getRemindedFriendHour());
            statement.setInt(3, newUserFriendsData.getFriendBirthdayDate().getRemindedFriendMinutes());
            statement.setString(4, newUserFriendsData.getFriendBirthdayDate().getPeriodTimeEnum().name());
            statement.setInt(5, newUserFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday());
            statement.setString(6, newUserFriendsData.getAboutFriend().getNameFriend());
            statement.setLong(7, ReadIdMySQL.readIdFriendsDataRowByIdUserAndFriendNameAndDateFriendBirthday(idUser, oldUserFriendsData.getAboutFriend().getNameFriend(), String.valueOf(oldUserFriendsData.getFriendBirthdayDate().getFriendDate())));
            statement.executeUpdate();
            logger.info("Edit User's Friend Data was successful!");
        } catch (SQLException e) {
            logger.error("Cannot Edit User's Friend Data!", e);
            throw new DAOException("Cannot Edit User's Friend Data!", e);
        }
        return true;
    }

    @Override
    public boolean deleteUserFriendsData(final UserFriendsData userFriendsData) throws DAOException {
        long idUser = ReadIdMySQL.readIdUser(userFriendsData.getUser().getEmail(), userFriendsData.getUser().getPassword());

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.deleteUserFriendsDataByIdUserFriendDate())) {
            statement.setLong(1, ReadIdMySQL.readIdFriendsDataRowByIdUserAndFriendNameAndDateFriendBirthday(idUser, userFriendsData.getAboutFriend().getNameFriend(), String.valueOf(userFriendsData.getFriendBirthdayDate().getFriendDate())));
            statement.executeUpdate();
            logger.info("Delete User's Friend Data was successful!");
        } catch (SQLException e) {
            logger.error("Cannot Delete User's Friend Data!", e);
            throw new DAOException("Cannot Delete User's Friend Data!", e);
        }
        return true;
    }


}
