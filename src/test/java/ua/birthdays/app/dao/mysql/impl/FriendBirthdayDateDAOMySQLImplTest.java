package ua.birthdays.app.dao.mysql.impl;

import org.junit.jupiter.api.Test;
import ua.birthdays.app.dao.Constant;
import ua.birthdays.app.dao.FriendBirthdayDateDAO;

import static org.junit.jupiter.api.Assertions.*;

class FriendBirthdayDateDAOMySQLImplTest {

    @Test
    void createFriendBirthdayDate() {
        assertDoesNotThrow(() -> {
            FriendBirthdayDateDAO friendBirthdayDateDAO = new FriendBirthdayDateDAOMySQLImpl();
            friendBirthdayDateDAO.createFriendBirthdayDate(Constant.FRIEND_BIRTHDAY_DATE);
        });
    }
}