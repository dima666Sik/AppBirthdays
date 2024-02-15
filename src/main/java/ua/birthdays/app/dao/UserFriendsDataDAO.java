package ua.birthdays.app.dao;

import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface UserFriendsDataDAO {
    boolean createUserFriendsData(final UserFriendsData userFriendsData, final long idAboutFriendRow, final long idFriendBirthdayDateRow);

    List<UserFriendsData> readAllUserFriendsDataDefault(final User user);

    List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(final User user);

    List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(final User user);

    List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(final User user);

    List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(final User user);

    boolean updateUserFriendsData(final UserFriendsData oldUserFriendsData, final UserFriendsData newUserFriendsData);

    boolean deleteUserFriendsData(final UserFriendsData userFriendsData);
}
