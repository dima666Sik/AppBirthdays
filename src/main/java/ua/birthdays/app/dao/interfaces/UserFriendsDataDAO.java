package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface UserFriendsDataDAO {
    boolean createUserFriendsData(UserFriendsData userFriendsData, long idAboutFriendRow, long idFriendBirthdayDateRow) throws DAOException;
    List<UserFriendsData> readAllUserFriendsData(User user) throws DAOException;
    boolean updateUserFriendsData(int idUserFriendsData) throws DAOException;
    boolean deleteUserFriendsData(int idUserFriendsData) throws DAOException;
}
