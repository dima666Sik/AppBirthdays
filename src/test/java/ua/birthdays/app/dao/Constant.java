package ua.birthdays.app.dao;

import ua.birthdays.app.dao.env.PeriodTimeEnum;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.time.LocalDate;

public final class Constant {
    private Constant() {
    }

    public static final AboutFriend ABOUT_FRIEND = new AboutFriend("TempFriend");
    public static final FriendBirthdayDate FRIEND_BIRTHDAY_DATE = new FriendBirthdayDate(
            LocalDate.of(2020, 1, 1),
            10,
            10,
            PeriodTimeEnum.PM,
            10
    );
    public static final User USER = new User(
            "fName",
            "lName",
            "e-mail",
            "password"
    );
    public static final UserFriendsData USER_FRIENDS_DATA = new UserFriendsData(
            FRIEND_BIRTHDAY_DATE,
            ABOUT_FRIEND,
            USER
    );
}