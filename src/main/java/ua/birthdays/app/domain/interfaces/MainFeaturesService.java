package ua.birthdays.app.domain.interfaces;

import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface MainFeaturesService {
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
