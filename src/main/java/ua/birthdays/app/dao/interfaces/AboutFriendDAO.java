package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.AboutFriend;

public interface AboutFriendDAO {
    long createAboutFriend(AboutFriend aboutFriend) throws DAOException;
}
