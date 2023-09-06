package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.interfaces.AboutFriendDAO;
import ua.birthdays.app.dao.interfaces.FriendBirthdayDateDAO;
import ua.birthdays.app.dao.interfaces.UserDAO;
import ua.birthdays.app.dao.interfaces.UserFriendsDataDAO;
import ua.birthdays.app.dao.mysql.impl.AboutFriendDAOMySQLImpl;
import ua.birthdays.app.dao.mysql.impl.FriendBirthdayDateDAOMySQLImpl;
import ua.birthdays.app.dao.mysql.impl.UserDAOMySQLImpl;
import ua.birthdays.app.dao.mysql.impl.UserFriendsDataDAOMySQLImpl;

/**
 * Factory for creating data access objects (DAO).
 * Used to obtain instances of DAO implementations for specific objects.
 */
public class DAOFactory {
    public static UserDAO getUserAuthDao() {
        return new UserDAOMySQLImpl();
    }

    public static UserFriendsDataDAO getUserFriendsDataDAO() {
        return new UserFriendsDataDAOMySQLImpl();
    }

    public static FriendBirthdayDateDAO getFriendBirthdayDate() {
        return new FriendBirthdayDateDAOMySQLImpl();
    }

    public static AboutFriendDAO getAboutFriend() {
        return new AboutFriendDAOMySQLImpl();
    }
}
