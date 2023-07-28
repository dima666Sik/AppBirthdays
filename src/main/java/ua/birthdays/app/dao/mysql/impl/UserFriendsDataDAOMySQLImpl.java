package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.env.EnumDBNameTables;
import ua.birthdays.app.dao.env.PeriodTimeEnum;
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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserFriendsDataDAOMySQLImpl implements UserFriendsDataDAO {
    private final static Logger logger = LogManager.getLogger(UserFriendsDataDAOMySQLImpl.class.getName());
    private List<UserFriendsData> userFriendsDataList;

    @Override
    public boolean createUserFriendsData(UserFriendsData userFriendsData, long idAboutFriendRow, long idFriendBirthdayDateRow) throws DAOException {
        long idUser = ReadIdMySQL.readIdUser(userFriendsData.getUser().getEmail(), userFriendsData.getUser().getPassword());

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

        if(UtilDAO.isUserFriendsDataRowExists(idUser,userFriendsData.getAboutFriend().getNameFriend(),userFriendsData.getFriendBirthdayDate().getFriendDate())){
            logger.warn("You have the same info into table about ufd!");
            return false;
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.createUserFriendsData())) {
            statement.setLong(1, idAboutFriendRow);
            statement.setLong(2, idFriendBirthdayDateRow);
            statement.setLong(3,idUser);
            statement.executeUpdate();
            logger.info("Create createUserFriendsData was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create createUserFriendsData!", e);
            throw new DAOException("Cannot create createUserFriendsData!", e);
        }
        return true;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDefault(User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

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
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdDescendingFriendBirthdayDate())) {
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
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdAscendingFriendBirthdayDate())) {
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
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdDescendingNameFriend())) {
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
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(User user) throws DAOException {
        userFriendsDataList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.findUserFriendsDataByUserIdAscendingNameFriend())) {
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
        } catch (SQLException e) {
            logger.error("Cannot Read ALL User Friends Data!", e);
            throw new DAOException("Cannot Read ALL User Friends Data!", e);
        }
        return userFriendsDataList;
    }

    @Override
    public boolean updateUserFriendsData(UserFriendsData oldUserFriendsData, UserFriendsData newUserFriendsData) throws DAOException {
        long idUser = ReadIdMySQL.readIdUser(newUserFriendsData.getUser().getEmail(), newUserFriendsData.getUser().getPassword());
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.updateUserFriendsDataByIdUserFriendDateAndUserIdAndFriendDateAndFriendName())) {
            System.out.println("++++++++++++"+String.valueOf(oldUserFriendsData.getFriendBirthdayDate().getFriendDate()));
            statement.setDate(1, Date.valueOf(newUserFriendsData.getFriendBirthdayDate().getFriendDate()));
            statement.setInt(2, newUserFriendsData.getFriendBirthdayDate().getRemindedFriendHour());
            statement.setInt(3, newUserFriendsData.getFriendBirthdayDate().getRemindedFriendMinutes());
            statement.setString(4, newUserFriendsData.getFriendBirthdayDate().getPeriodTimeEnum().name());
            statement.setInt(5, newUserFriendsData.getFriendBirthdayDate().getRemindedCountDaysBeforeBirthday());
            statement.setString(6, newUserFriendsData.getAboutFriend().getNameFriend());
            statement.setLong(7, ReadIdMySQL.readIdFriendsDataRowByIdUserAndFriendNameAndDateFriendBirthday(idUser, oldUserFriendsData.getAboutFriend().getNameFriend(), String.valueOf(oldUserFriendsData.getFriendBirthdayDate().getFriendDate())));
            statement.executeUpdate();
            logger.info("Create createUserFriendsData was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create createUserFriendsData!", e);
            throw new DAOException("Cannot create createUserFriendsData!", e);
        }
        return true;
    }

    @Override
    public boolean deleteUserFriendsData(User user, String nameFriend, String friendBirthdayDate) throws DAOException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.existsByUserIdAndFriendNameAndFriendDate())) {
            statement.setLong(1, ReadIdMySQL.readIdUser(user.getEmail(),user.getPassword()));
            statement.setString(2, nameFriend);
            statement.setDate(3, Date.valueOf(friendBirthdayDate));
            statement.executeUpdate();
            logger.info("Create createUserFriendsData was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create createUserFriendsData!", e);
            throw new DAOException("Cannot create createUserFriendsData!", e);
        }
        return true;
    }


}
