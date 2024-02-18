package ua.birthdays.app.dao;

import ua.birthdays.app.model.AboutFriend;

@FunctionalInterface
public interface AboutFriendDAO {
    long createAboutFriend(final AboutFriend aboutFriend);
}
