package ua.birthdays.app.domain.interfaces;

import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public interface MainFeaturesService {
    boolean createUserFriendsData(UserFriendsData userFriendsData);
    boolean createFriendBirthdayDate(FriendBirthdayDate friendBirthdayDate);
    boolean createAboutFriend(AboutFriend aboutFriend);
    List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(User user);
    List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(User user);
    List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(User user);
    List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(User user);
    List<UserFriendsData> readAllUserFriendsDataByDefault(User user);
    boolean updateUserFriendsData(UserFriendsData oldUserFriendsData, UserFriendsData newUserFriendsData);
    boolean deleteUserFriendsData(User user, String nameFriend, String friendBirthdayDate);
}
