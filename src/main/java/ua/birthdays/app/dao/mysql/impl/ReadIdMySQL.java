package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.query.QueryAboutFriend;
import ua.birthdays.app.dao.query.QueryFriendBirthdayDate;
import ua.birthdays.app.dao.query.QueryUser;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReadIdMySQL {

    final static Logger logger = LogManager.getLogger(FileReader.class);

    public static int readIdUser(String email, String password) {
        int id = 0;
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

    public static int readIdAboutFriend(AboutFriend aboutFriend) {
        int id = 0;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryAboutFriend.findAboutFriendByNameFriend());
        ) {
            statement.setString(1, aboutFriend.getNameFriend());
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    id = resultSet.getInt("id_about_friend");
                }
                if (id == 0) {
                    logger.error("Information About Friend is not found. Check table in db!");
                }
                logger.info("Check id phone exist successful");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return id;
    }

    public static int readIdFriendBirthdayDate(FriendBirthdayDate friendBirthdayDate) {
        int id = 0;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryFriendBirthdayDate.findFriendBirthdayDateByAllInformation());
        ) {
            statement.setInt(1, friendBirthdayDate.getMonthDayBirthdayDate().getDayOfMonth());
            statement.setInt(2, friendBirthdayDate.getMonthBirthdayDate().getValue());
            statement.setInt(3, friendBirthdayDate.getYearBirthdayDate().getValue());
            statement.setInt(4, friendBirthdayDate.getHour());
            statement.setInt(5, friendBirthdayDate.getMinutes());
            statement.setInt(6, friendBirthdayDate.getRemindedCountDaysBeforeBirthday());
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    id = resultSet.getInt("id_friend_birthday_date");
                }
                if (id == 0) {
                    logger.error("Information Friend Birthday Date is not found. Check table in db!");
                }
                logger.info("Check id card exist successful");
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return id;
    }

}