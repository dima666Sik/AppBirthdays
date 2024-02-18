package ua.birthdays.app.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.AboutFriendDAO;
import ua.birthdays.app.dao.FriendBirthdayDateDAO;
import ua.birthdays.app.dao.UserFriendsDataDAO;
import ua.birthdays.app.dao.util.DAOFactory;
import ua.birthdays.app.service.UserFriendsDataService;
import ua.birthdays.app.model.AboutFriend;
import ua.birthdays.app.model.FriendBirthdayDate;
import ua.birthdays.app.model.User;
import ua.birthdays.app.model.UserFriendsData;

import java.util.List;

/**
 * Implementation of the {@link UserFriendsDataService} interface that provides various operations
 * for managing user friend's data.
 */
public class UserFriendsDataServiceImpl implements UserFriendsDataService {
    private static final Logger logger = LogManager.getLogger(UserFriendsDataServiceImpl.class.getName());

    private final UserFriendsDataDAO userFriendsDataDAO;
    private final FriendBirthdayDateDAO friendBirthdayDateDAO;
    private final AboutFriendDAO aboutFriendDAO;

    public UserFriendsDataServiceImpl() {
        userFriendsDataDAO = DAOFactory.getUserFriendsDataDAO();
        friendBirthdayDateDAO = DAOFactory.getFriendBirthdayDate();
        aboutFriendDAO = DAOFactory.getAboutFriend();
    }

    /**
     * Creates user friend's data by persisting relevant information into the database.
     *
     * @param userFriendsData The user friend's data to be created.
     * @return True if the creation was successful, otherwise false.
     */
    @Override
    public boolean createUserFriendsData(final UserFriendsData userFriendsData) {
        long idAboutFriendRow;
        long idFriendBirthdayDateRow;
        if ((idAboutFriendRow = aboutFriendDAO.createAboutFriend(userFriendsData.getAboutFriend())) == -1)
            return false;
        if ((idFriendBirthdayDateRow = friendBirthdayDateDAO.createFriendBirthdayDate(userFriendsData.getFriendBirthdayDate())) == -1)
            return false;
        return userFriendsDataDAO.createUserFriendsData(userFriendsData, idAboutFriendRow, idFriendBirthdayDateRow);
    }

    /**
     * Creates friend's birthday date by persisting relevant information into the database.
     *
     * @param friendBirthdayDate The friend's birthday date to be created.
     * @return True if the creation was successful, otherwise false.
     */
    @Override
    public boolean createFriendBirthdayDate(final FriendBirthdayDate friendBirthdayDate) {
        return friendBirthdayDateDAO.createFriendBirthdayDate(friendBirthdayDate) != -1;
    }

    /**
     * Creates friend's about by persisting relevant information into the database.
     *
     * @param aboutFriend The friend's about to be created.
     * @return True if the creation was successful, otherwise false.
     */
    @Override
    public boolean createAboutFriend(final AboutFriend aboutFriend) {
        return aboutFriendDAO.createAboutFriend(aboutFriend) != -1;
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByFriendBirthdayDate(final User user) {
        return userFriendsDataDAO.readAllUserFriendsDataDescendingByFriendBirthdayDate(user);
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByFriendBirthdayDate(final User user) {
        return userFriendsDataDAO.readAllUserFriendsDataAscendingByFriendBirthdayDate(user);
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataDescendingByNameFriend(final User user) {
        return userFriendsDataDAO.readAllUserFriendsDataDescendingByNameFriend(user);
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataAscendingByNameFriend(final User user) {
        return userFriendsDataDAO.readAllUserFriendsDataAscendingByNameFriend(user);
    }

    @Override
    public List<UserFriendsData> readAllUserFriendsDataByDefault(final User user) {
        return userFriendsDataDAO.readAllUserFriendsDataDefault(user);
    }


    @Override
    public boolean updateUserFriendsData(final UserFriendsData oldUserFriendsData, final UserFriendsData newUserFriendsData) {
        return userFriendsDataDAO.updateUserFriendsData(oldUserFriendsData, newUserFriendsData);
    }

    /**
     * Deletes user friend's data from the database.
     *
     * @param userFriendsData The user friend's data to be deleted.
     * @return True if the deletion was successful, otherwise false.
     */
    @Override
    public boolean deleteUserFriendsData(final UserFriendsData userFriendsData) {
        return userFriendsDataDAO.deleteUserFriendsData(userFriendsData);
    }

}
