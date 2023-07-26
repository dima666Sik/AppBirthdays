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
    List<UserFriendsData> readAllUserFriendsData(User user);
}
