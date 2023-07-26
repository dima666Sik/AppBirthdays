package ua.birthdays.app.domain.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

class MainFeaturesServiceImplTest {

    MainFeaturesService mainFeaturesService = new MainFeaturesServiceImpl();;

    @Test
    void createUserFriendsData() {

        FriendBirthdayDate friendBirthdayDate = new FriendBirthdayDate(
                LocalDate.of(2002, Month.APRIL, 8),
                9,20, 3);

        AboutFriend aboutFriend = new AboutFriend("Dimaaasss Kgl");
        User user = new User("ff", "ff", "ff", "A44BA123189855990795E3260A64B34CDAE6B29BF1C941818A34CBA8BBC45575");

        UserFriendsData userFriendsData = new UserFriendsData(friendBirthdayDate,aboutFriend,user);

        boolean isCreatedUser = mainFeaturesService.createUserFriendsData(userFriendsData);

        Assertions.assertTrue(isCreatedUser);
    }

    @Test
    void createFriendBirthdayDate() {
        FriendBirthdayDate friendBirthdayDate = new FriendBirthdayDate(
                LocalDate.of(2222, Month.MAY, 18),
                7,28, 33);

        boolean isCreated = mainFeaturesService.createFriendBirthdayDate(friendBirthdayDate);
        Assertions.assertTrue(isCreated);

    }

    @Test
    void createAboutFriend() {
        AboutFriend aboutFriend = new AboutFriend("Help 1");

        boolean isCreated = mainFeaturesService.createAboutFriend(aboutFriend);
        Assertions.assertTrue(isCreated);
    }

    @Test
    void readAllUserFriendsData() {
        User user = new User("ff", "ff", "ff", "A44BA123189855990795E3260A64B34CDAE6B29BF1C941818A34CBA8BBC45575");

        List<UserFriendsData> userFriendsDataList = mainFeaturesService.readAllUserFriendsData(user);

        Assertions.assertNotNull(userFriendsDataList);
    }
}