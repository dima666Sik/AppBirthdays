package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.FriendBirthdayDate;

public interface FriendBirthdayDateDAO {
    long createFriendBirthdayDate(FriendBirthdayDate friendBirthdayDate) throws DAOException;
}
