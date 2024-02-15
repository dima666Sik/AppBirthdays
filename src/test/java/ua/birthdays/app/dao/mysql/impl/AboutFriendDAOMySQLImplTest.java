package ua.birthdays.app.dao.mysql.impl;

import org.junit.jupiter.api.Test;
import ua.birthdays.app.dao.AboutFriendDAO;
import ua.birthdays.app.dao.Constant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AboutFriendDAOMySQLImplTest {
    @Test
    void createAboutFriend() {
        assertDoesNotThrow(() -> {
            AboutFriendDAO aboutFriendDAO = new AboutFriendDAOMySQLImpl();
            aboutFriendDAO.createAboutFriend(Constant.ABOUT_FRIEND);
        });
    }
}