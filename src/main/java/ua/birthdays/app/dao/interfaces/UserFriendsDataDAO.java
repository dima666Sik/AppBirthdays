package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface UserFriendsDataDAO {
    boolean createUserFriendsData(final UserFriendsData userFriendsData, final long idAboutFriendRow, final long idFriendBirthdayDateRow) throws DAOException;

    List<UserFriendsData> readAllUserFriendsDataDefault(final User user) throws DAOException;

    List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(final User user) throws DAOException;

    List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(final User user) throws DAOException;

    List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(final User user) throws DAOException;

    List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(final User user) throws DAOException;

    boolean updateUserFriendsData(final UserFriendsData oldUserFriendsData, final UserFriendsData newUserFriendsData) throws DAOException;

    boolean deleteUserFriendsData(final UserFriendsData userFriendsData) throws DAOException;
}
