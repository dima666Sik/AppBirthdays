package ua.birthdays.app.dao;

import ua.birthdays.app.models.FriendBirthdayDate;

@FunctionalInterface
public interface FriendBirthdayDateDAO {
    long createFriendBirthdayDate(final FriendBirthdayDate friendBirthdayDate);
}
