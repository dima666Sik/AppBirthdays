package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface FriendBirthdayDateDAO {
    long createFriendBirthdayDate(FriendBirthdayDate friendBirthdayDate) throws DAOException;
}
