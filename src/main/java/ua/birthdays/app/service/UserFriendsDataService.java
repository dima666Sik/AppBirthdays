package ua.birthdays.app.service;

import ua.birthdays.app.model.AboutFriend;
import ua.birthdays.app.model.FriendBirthdayDate;
import ua.birthdays.app.model.User;
import ua.birthdays.app.model.UserFriendsData;

import java.util.List;

public interface UserFriendsDataService {
    boolean createUserFriendsData(final UserFriendsData userFriendsData);

    boolean createFriendBirthdayDate(final FriendBirthdayDate friendBirthdayDate);

    boolean createAboutFriend(final AboutFriend aboutFriend);

    List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(final User user);

    List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(final User user);

    List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(final User user);

    List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(final User user);

    List<UserFriendsData> readAllUserFriendsDataByDefault(final User user);

    boolean updateUserFriendsData(final UserFriendsData oldUserFriendsData, final UserFriendsData newUserFriendsData);

    boolean deleteUserFriendsData(final UserFriendsData userFriendsData);
}
