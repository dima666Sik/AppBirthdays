package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.dao.UserFriendsDataDAO;
import ua.birthdays.app.dao.exceptions.DataRemovingException;
import ua.birthdays.app.dao.exceptions.DataAddingException;
import ua.birthdays.app.dao.exceptions.DataReadingException;
import ua.birthdays.app.dao.exceptions.DataModificationException;
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

/**
 * This class is an implementation of the UserFriendsDataDAO interface.
 * It provides methods for creating, reading, updating, and deleting user friends data.
 */
public class UserFriendsDataDAOMySQLImpl implements UserFriendsDataDAO {
    private static final Logger logger = LogManager.getLogger(UserFriendsDataDAOMySQLImpl.class.getName());
    private List<UserFriendsData> userFriendsDataList;

    /**
     * Creates user friends data in the database.
     *
     * @param userFriendsData         the user friends data to be created
     * @param idAboutFriendRow        the ID of the about a friend row
     * @param idFriendBirthdayDateRow the ID of the friend birthday date row
     * @return true if the user friends data was created, false if the user friends data already exists
     * @throws DataAddingException if an error occurs while creating the user friends data
     */
    @Override
    public boolean createUserFriendsData(final UserFriendsData userFriendsData,
                                         final long idAboutFriendRow,
                                         final long idFriendBirthdayDateRow) {
        long idUser = ReadIdMySQL.readIdUser(
                userFriendsData.getUser()
                               .getEmail(),
                userFriendsData.getUser()
                               .getPassword()
        );

        UtilDAO.isTableUserFriendDataExist();

        if (UtilDAO.isUserFriendsDataRowExists(idUser,
                userFriendsData.getAboutFriend()
                               .getNameFriend(),
                userFriendsData.getFriendBirthdayDate()
                               .getFriendDate())) {
            logger.warn("You have the same info into table about ufd!");
            return false;
        }

        try (var connection = DBConnector.getConnection();
             var statement
                     = connection.prepareStatement(QueryUserFriendsData.INSERT_USER_FRIENDS_DATA)) {
            statement.setLong(1, idAboutFriendRow);
            statement.setLong(2, idFriendBirthdayDateRow);
            statement.setLong(3, idUser);
            statement.executeUpdate();
            logger.info("Create row createUserFriendsData was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create row createUserFriendsData!", e);
            throw new DataAddingException("Cannot create row createUserFriendsData!", e);
        }
        return true;
    }

    /**
     * Reads all user friends data for a given user.
     *
     * @param user the user for whom to read the data
     * @return a list of user friends data for the given user
     * @throws DataReadingException if an error occurs while reading the user friends data
     */

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDefault(final User user) {
        userFriendsDataList = new ArrayList<>();

        UtilDAO.isTableAboutFriendExist();

        UtilDAO.isTableFriendBirthdayDateExist();

        UtilDAO.isTableUserFriendDataExist();

        try (var connection = DBConnector.getConnection();
             var statement
                     = connection.prepareStatement(QueryUserFriendsData.FIND_USER_FRIENDS_DATA_BY_USER_ID)) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data Default!", e);
            throw new DataReadingException("Cannot Read ALL User Friends Data Default!", e);
        }
        return userFriendsDataList;
    }

    /**
     * Reads all user friends data for a given user in descending order by friend birthday date.
     *
     * @param user the user for whom to read the data
     * @return a list of user friends data for the given user in descending order by friend birthday date
     * @throws DataReadingException if an error occurs while reading the user friends data
     */
    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(final User user) {
        userFriendsDataList = new ArrayList<>();

        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUserFriendsData.FIND_USER_FRIENDS_DATA_BY_USER_ID_DESCENDING_FRIEND_BIRTHDAY_DATE)) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data in Descending Ordering By Friend BirthdayDate!", e);
            throw new DataReadingException("Cannot Read ALL User Friends Data in Descending Ordering By Friend BirthdayDate!", e);
        }
        return userFriendsDataList;
    }

    /**
     * Reads all user friends data for a given user in ascending order by friend birthday date.
     *
     * @param user the user for whom to read the data
     * @return a list of user friends data for the given user in ascending order by friend birthday date
     * @throws DataReadingException if an error occurs while reading the user friends data
     */
    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(final User user) {
        userFriendsDataList = new ArrayList<>();

        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUserFriendsData.FIND_USER_FRIENDS_DATA_BY_USER_ID_ASCENDING_FRIEND_BIRTHDAY_DATE)) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data in Ascending Ordering By Friend BirthdayDate!", e);
            throw new DataReadingException("Cannot Read ALL User Friends Data in Ascending Ordering By Friend BirthdayDate!", e);
        }
        return userFriendsDataList;
    }

    /**
     * Reads all user friends data for a given user in descending order by name friend.
     *
     * @param user the user for whom to read the data
     * @return a list of user friends data for the given user in descending order by name friend
     * @throws DataReadingException if an error occurs while reading the user friends data
     */
    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(final User user) {
        userFriendsDataList = new ArrayList<>();

        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUserFriendsData.FIND_USER_FRIENDS_DATA_BY_USER_ID_DESCENDING_NAME_FRIEND)) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data in Descending Ordering By NameFriend!", e);
            throw new DataReadingException("Cannot Read ALL User Friends Data in Descending Ordering By NameFriend!", e);
        }
        return userFriendsDataList;
    }

    private void readByIdUserUserFriendData(User user, PreparedStatement statement) throws SQLException {
        statement.setLong(1, ReadIdMySQL.readIdUser(user.getEmail(), user.getPassword()));
        var resultSet = statement.executeQuery();

        while (resultSet.next()) {
            userFriendsDataList.add(
                    new UserFriendsData(
                            new FriendBirthdayDate(
                                    resultSet.getDate("friend_date")
                                             .toLocalDate(),
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

    /**
     * Reads all user friends data for a given user in ascending order by name friend.
     *
     * @param user the user for whom to read the data
     * @return a list of user friends data for the given user in ascending order by name friend
     * @throws DataReadingException if an error occurs while reading the user friends data
     */
    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(final User user) {
        userFriendsDataList = new ArrayList<>();

        try (var connection = DBConnector.getConnection();
             var statement
                     = connection.prepareStatement(QueryUserFriendsData.FIND_USER_FRIENDS_DATA_BY_USER_ID_ASCENDING_NAME_FRIEND)) {
            readByIdUserUserFriendData(user, statement);
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data in Ascending Ordering By NameFriend!", e);
            throw new DataReadingException("Cannot Read ALL User Friends Data in Ascending Ordering By NameFriend!", e);
        }
        return userFriendsDataList;
    }

    /**
     * Updates the user friends data in the database based on the given user friends data.
     *
     * @param oldUserFriendsData the user friends data to be updated
     * @param newUserFriendsData the updated user friends data
     * @return true if the user friends data was updated, false if the user friends data does not exist
     * @throws DataModificationException if an error occurs while updating the user friends data
     */
    @Override
    public boolean updateUserFriendsData(final UserFriendsData oldUserFriendsData,
                                         final UserFriendsData newUserFriendsData) {
        long idUser = ReadIdMySQL.readIdUser(
                newUserFriendsData.getUser()
                                  .getEmail(),
                newUserFriendsData.getUser()
                                  .getPassword()
        );

        if (UtilDAO.isUserFriendsDataRowExists(
                idUser,
                newUserFriendsData.getAboutFriend()
                                  .getNameFriend(),
                newUserFriendsData.getFriendBirthdayDate()
                                  .getFriendDate())) {
            logger.warn("You have the same info into table about ufd!");
            return false;
        }

        try (var connection = DBConnector.getConnection();
             var statement
                     = connection.prepareStatement(QueryUserFriendsData.UPDATE_USER_FRIENDS_DATA_BY_ID_USER_FRIEND_DATE)) {
            statement.setDate(1, Date.valueOf(newUserFriendsData.getFriendBirthdayDate()
                                                                .getFriendDate()));
            statement.setInt(2, newUserFriendsData.getFriendBirthdayDate()
                                                  .getRemindedFriendHour());
            statement.setInt(3, newUserFriendsData.getFriendBirthdayDate()
                                                  .getRemindedFriendMinutes());
            statement.setString(4, newUserFriendsData.getFriendBirthdayDate()
                                                     .getPeriodTimeEnum()
                                                     .name());
            statement.setInt(5, newUserFriendsData.getFriendBirthdayDate()
                                                  .getRemindedCountDaysBeforeBirthday());
            statement.setString(6, newUserFriendsData.getAboutFriend()
                                                     .getNameFriend());
            statement.setLong(7, ReadIdMySQL.readIdFriendsDataRowByIdUserAndFriendNameAndDateFriendBirthday(idUser, oldUserFriendsData.getAboutFriend()
                                                                                                                                      .getNameFriend(), String.valueOf(oldUserFriendsData.getFriendBirthdayDate()
                                                                                                                                                                                         .getFriendDate())));
            statement.executeUpdate();
            logger.info("Edit User's Friend Data was successful!");
        } catch (SQLException e) {
            logger.error("Cannot Edit User's Friend Data!", e);
            throw new DataModificationException("Cannot Edit User's Friend Data!", e);
        }
        return true;
    }

    /**
     * Deletes the user friends data from the database based on the given user friends data.
     *
     * @param userFriendsData the user friends data to be deleted
     * @return true if the user friends data was deleted, false if the user friends data does not exist
     * @throws DataRemovingException if an error occurs while deleting the user friends data
     */
    @Override
    public boolean deleteUserFriendsData(final UserFriendsData userFriendsData) {
        long idUser = ReadIdMySQL.readIdUser(
                userFriendsData.getUser()
                               .getEmail(),
                userFriendsData.getUser()
                               .getPassword()
        );

        try (var connection = DBConnector.getConnection();
             var statement
                     = connection.prepareStatement(QueryUserFriendsData.DELETE_USER_FRIENDS_DATA_BY_ID_USER_FRIEND_DATE)) {
            statement.setLong(1, ReadIdMySQL.readIdFriendsDataRowByIdUserAndFriendNameAndDateFriendBirthday(idUser, userFriendsData.getAboutFriend()
                                                                                                                                   .getNameFriend(), String.valueOf(userFriendsData.getFriendBirthdayDate()
                                                                                                                                                                                   .getFriendDate())));
            statement.executeUpdate();
            logger.info("Delete User's Friend Data was successful!");
        } catch (SQLException e) {
            logger.error("Cannot Delete User's Friend Data!", e);
            throw new DataRemovingException("Cannot Delete User's Friend Data!", e);
        }
        return true;
    }


}
