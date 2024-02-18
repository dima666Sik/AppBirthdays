package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.AboutFriendDAO;
import ua.birthdays.app.dao.FriendBirthdayDateDAO;
import ua.birthdays.app.dao.UserDAO;
import ua.birthdays.app.dao.UserFriendsDataDAO;
import ua.birthdays.app.dao.mysql.impl.AboutFriendDAOImpl;
import ua.birthdays.app.dao.mysql.impl.FriendBirthdayDateDAOImpl;
import ua.birthdays.app.dao.mysql.impl.UserDAOImpl;
import ua.birthdays.app.dao.mysql.impl.UserFriendsDataDAOImpl;

/**
 * Factory for creating data access objects (DAO).
 * Used to obtain instances of DAO implementations for specific objects.
 */
public final class DAOFactory {
    private static final UserDAO USER_DAO = new UserDAOImpl();
    private static final UserFriendsDataDAOImpl USER_FRIENDS_DATA_DAO = new UserFriendsDataDAOImpl();
    private static final FriendBirthdayDateDAOImpl FRIEND_BIRTHDAY_DATE_DAO = new FriendBirthdayDateDAOImpl();
    private static final AboutFriendDAOImpl ABOUT_FRIEND_DAO = new AboutFriendDAOImpl();

    private DAOFactory() {
    }

    public static UserDAO getUserAuthDao() {
        return USER_DAO;
    }

    public static UserFriendsDataDAO getUserFriendsDataDAO() {
        return USER_FRIENDS_DATA_DAO;
    }

    public static FriendBirthdayDateDAO getFriendBirthdayDate() {
        return FRIEND_BIRTHDAY_DATE_DAO;
    }

    public static AboutFriendDAO getAboutFriend() {
        return ABOUT_FRIEND_DAO;
    }
}
