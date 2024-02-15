package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.AboutFriendDAO;
import ua.birthdays.app.dao.FriendBirthdayDateDAO;
import ua.birthdays.app.dao.UserDAO;
import ua.birthdays.app.dao.UserFriendsDataDAO;
import ua.birthdays.app.dao.mysql.impl.AboutFriendDAOMySQLImpl;
import ua.birthdays.app.dao.mysql.impl.FriendBirthdayDateDAOMySQLImpl;
import ua.birthdays.app.dao.mysql.impl.UserDAOMySQLImpl;
import ua.birthdays.app.dao.mysql.impl.UserFriendsDataDAOMySQLImpl;

/**
 * Factory for creating data access objects (DAO).
 * Used to obtain instances of DAO implementations for specific objects.
 */
public final class DAOFactory {
    private DAOFactory() {
    }

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
