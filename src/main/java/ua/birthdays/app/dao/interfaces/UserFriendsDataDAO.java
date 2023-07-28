package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface UserFriendsDataDAO {
    boolean createUserFriendsData(UserFriendsData userFriendsData, long idAboutFriendRow, long idFriendBirthdayDateRow) throws DAOException;
    List<UserFriendsData> readAllUserFriendsDataDefault(User user) throws DAOException;
    List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(User user)throws DAOException;
    List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(User user)throws DAOException;
    List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(User user)throws DAOException;
    List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(User user)throws DAOException;
    boolean updateUserFriendsData(UserFriendsData oldUserFriendsData, UserFriendsData newUserFriendsData) throws DAOException;
    boolean deleteUserFriendsData(UserFriendsData userFriendsData) throws DAOException;
}
