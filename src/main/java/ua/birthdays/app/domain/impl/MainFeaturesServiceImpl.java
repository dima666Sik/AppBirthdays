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
    public List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(User user) {
        try {
            return userFriendsDataDAO.readAllUserFriendsDataDescendingByFriendBirthdayDate(user);
        } catch (DAOException e) {
            logger.error("Cannot read all descending user friend's data by friend birthday date!", e);
            return null;
        }
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(User user) {
        try {
            return userFriendsDataDAO.readAllUserFriendsDataAscendingByFriendBirthdayDate(user);
        } catch (DAOException e) {
            logger.error("Cannot read all ascending user friend's data by friend birthday date!", e);
            return null;
        }
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(User user) {
        try {
            return userFriendsDataDAO.readAllUserFriendsDataDescendingByNameFriend(user);
        } catch (DAOException e) {
            logger.error("Cannot read all descending user friend's data by friend's name!", e);
            return null;
        }
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(User user) {
        try {
            return userFriendsDataDAO.readAllUserFriendsDataAscendingByNameFriend(user);
        } catch (DAOException e) {
            logger.error("Cannot read all ascending user friend's data by friend's name!", e);
            return null;
        }
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataByDefault(User user) {
        try {
            return userFriendsDataDAO.readAllUserFriendsDataDefault(user);
        } catch (DAOException e) {
            logger.error("Cannot read all user friend's data!", e);
            return null;
        }
    }

    @Override
    public boolean updateUserFriendsData(UserFriendsData oldUserFriendsData, UserFriendsData newUserFriendsData) {
        try {
            return userFriendsDataDAO.updateUserFriendsData(oldUserFriendsData, newUserFriendsData);
        } catch (DAOException e) {
            logger.error("Cannot update user friend's data!", e);
            return false;
        }
    }

    @Override
    public boolean deleteUserFriendsData(User user, String nameFriend, String friendBirthdayDate) {
        try {
            return userFriendsDataDAO.deleteUserFriendsData(user, nameFriend, friendBirthdayDate);
        } catch (DAOException e) {
            logger.error("Cannot delete user friend's data!", e);
            return false;
        }
    }

}
