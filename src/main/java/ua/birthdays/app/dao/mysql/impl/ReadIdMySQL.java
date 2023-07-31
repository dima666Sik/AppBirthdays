package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.query.QueryUser;
import ua.birthdays.app.dao.query.QueryUserFriendsData;
import ua.birthdays.app.dao.util.DBConnector;

import java.io.FileReader;
import java.sql.*;

public class ReadIdMySQL {

    final static Logger logger = LogManager.getLogger(FileReader.class);

    public static long readIdUser(String email, String password) {
        long id = 0;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUser.findUserByEmailAndPassword());
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    id = resultSet.getInt("id_user");
                }
                if (id == 0) {
                    logger.error("User is not found. Check table in db!");
                }
                logger.info("Check id user exist successful");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return id;
    }

    public static long readIdFriendsDataRowByIdUserAndFriendNameAndDateFriendBirthday(long idUser, String friendsName, String dateFriendBirthday) throws DAOException {
        long idFriendsData = 0;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUserFriendsData.existsByUserIdAndFriendNameAndFriendDate())) {
            statement.setLong(1, idUser);
            statement.setString(2, friendsName);
            statement.setDate(3, Date.valueOf(dateFriendBirthday));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) idFriendsData = resultSet.getLong("id_user_friends_data");
            }
        } catch (SQLException e) {
            logger.error("Cannot check ufd row exists!", e);
            throw new DAOException("Cannot check ufd row exists!", e);
        }
        return idFriendsData;
    }
}