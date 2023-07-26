package ua.birthdays.app.domain.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.AboutFriendDAO;
import ua.birthdays.app.dao.interfaces.FriendBirthdayDateDAO;
import ua.birthdays.app.dao.interfaces.UserFriendsDataDAO;
import ua.birthdays.app.dao.util.DAOFactory;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.AboutFriend;
import ua.birthdays.app.models.FriendBirthdayDate;
import ua.birthdays.app.models.User;
import ua.birthdays.app.models.UserFriendsData;

import java.util.List;

public class MainFeaturesServiceImpl implements MainFeaturesService {
    private final static Logger logger = LogManager.getLogger(MainFeaturesServiceImpl.class.getName());

    private final UserFriendsDataDAO userFriendsDataDAO;
    private final FriendBirthdayDateDAO friendBirthdayDateDAO;
    private final AboutFriendDAO aboutFriendDAO;

    public MainFeaturesServiceImpl() {
        userFriendsDataDAO = DAOFactory.getUserFriendsDataDAO();
        friendBirthdayDateDAO = DAOFactory.getFriendBirthdayDate();
        aboutFriendDAO = DAOFactory.getAboutFriend();
    }

    @Override
    public boolean createUserFriendsData(UserFriendsData userFriendsData) {
        try {
            long idAboutFriendRow, idFriendBirthdayDateRow;
            if ((idAboutFriendRow = aboutFriendDAO.createAboutFriend(userFriendsData.getAboutFriend())) == -1)
                return false;
            if ((idFriendBirthdayDateRow = friendBirthdayDateDAO.createFriendBirthdayDate(userFriendsData.getFriendBirthdayDate())) == -1)
                return false;
            return userFriendsDataDAO.createUserFriendsData(userFriendsData, idAboutFriendRow, idFriendBirthdayDateRow);
        } catch (DAOException e) {
            logger.error("Cannot create user friend's birthday!", e);
            return false;
        }
    }

    @Override
    public boolean createFriendBirthdayDate(FriendBirthdayDate friendBirthdayDate) {
        try {
            return friendBirthdayDateDAO.createFriendBirthdayDate(friendBirthdayDate) != -1;
        } catch (DAOException e) {
            logger.error("Cannot create friend's birthday date!", e);
            return false;
        }
    }

    @Override
    public boolean createAboutFriend(AboutFriend aboutFriend) {
        try {
            return aboutFriendDAO.createAboutFriend(aboutFriend) != -1;
        } catch (DAOException e) {
            logger.error("Cannot create user friend's birthday!", e);
            return false;
        }
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsData(User user) {
        try {
            return userFriendsDataDAO.readAllUserFriendsData(user);
        } catch (DAOException e) {
            logger.error("Cannot read all user friend's data!", e);
            return null;
        }
    }


}
