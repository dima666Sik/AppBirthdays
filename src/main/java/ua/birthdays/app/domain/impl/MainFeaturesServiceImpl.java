package ua.birthdays.app.domain.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.UserFriendsDataDAO;
import ua.birthdays.app.dao.util.DAOFactory;
import ua.birthdays.app.domain.interfaces.MainFeaturesService;
import ua.birthdays.app.models.UserFriendsData;

public class MainFeaturesServiceImpl implements MainFeaturesService {
    private final static Logger logger = LogManager.getLogger(MainFeaturesServiceImpl.class.getName());

    private final UserFriendsDataDAO userFriendsDataDAO;

    public MainFeaturesServiceImpl() {
        userFriendsDataDAO = DAOFactory.getUserFriendsDataDAO();
    }

    @Override
    public boolean createUserFriendsData(UserFriendsData userFriendsData) {
        try {
            return userFriendsDataDAO.createUserFriendsData(userFriendsData);
        } catch (DAOException e) {
            logger.error("Cannot create user friend's birthday!", e);
            return false;
        }
    }
}
