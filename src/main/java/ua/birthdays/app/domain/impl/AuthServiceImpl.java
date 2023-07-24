package ua.birthdays.app.domain.impl;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.UserDAO;
import ua.birthdays.app.dao.util.DAOFactory;
import ua.birthdays.app.domain.exceptions.DomainException;
import ua.birthdays.app.domain.interfaces.AuthService;
import ua.birthdays.app.domain.util.Encryption;
import ua.birthdays.app.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthServiceImpl implements AuthService {
    private final static Logger logger = LogManager.getLogger(AuthServiceImpl.class.getName());

    private final UserDAO userDAO;

    public AuthServiceImpl() {
        userDAO = DAOFactory.getUserAuthDao();
        logger.info("Getting user auth instance!");
    }

    @Override
    public boolean registration(String firstName, String lastName, String email, char[] password) {

        try {
            User user = new User(firstName, lastName, email, Encryption.encryptionSHA256(password));
            try {
                return userDAO.createUser(user);
            } catch (DAOException e) {
                logger.error("Cannot create user!", e);
                return false;
            }
        } catch (DomainException e) {
            logger.error("Cryptographic algorithm isn't available!", e);
            return false;
        }
    }

    @Override
    public User authorization(String email, char[] password) {
        User user = null;

        try {
            user = userDAO.findUserByEmailAndPassword(email, Encryption.encryptionSHA256(password));
            if (user == null) {
                logger.warn("User is empty! Finding user wasn't successful!");
            }
        } catch (DAOException e) {
            logger.error("Cannot create user!", e);
        } catch (DomainException e) {
            logger.error("Cryptographic algorithm isn't available!", e);
        }

        return user;
    }


}
