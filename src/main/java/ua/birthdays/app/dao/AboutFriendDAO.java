package ua.birthdays.app.dao;

import ua.birthdays.app.models.AboutFriend;

@FunctionalInterface
public interface AboutFriendDAO {
    long createAboutFriend(final AboutFriend aboutFriend);
}
