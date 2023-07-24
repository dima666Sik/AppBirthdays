package ua.birthdays.app.dao.util;

import ua.birthdays.app.dao.interfaces.UserDAO;
import ua.birthdays.app.dao.interfaces.UserFriendsDataDAO;
import ua.birthdays.app.dao.mysql.impl.UserDAOMySQLImpl;
import ua.birthdays.app.dao.mysql.impl.UserFriendsDataDAOMySQLImpl;

public class DAOFactory {
    public static UserDAO getUserAuthDao() {
        return new UserDAOMySQLImpl();
    }
    public static UserFriendsDataDAO getUserFriendsDataDAO(){return new UserFriendsDataDAOMySQLImpl();}
}