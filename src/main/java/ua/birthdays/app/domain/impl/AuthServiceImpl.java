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

/**
 * Implementation of {@link AuthService} interface for user authentication and registration.
 */
public class AuthServiceImpl implements AuthService {
    private final static Logger logger = LogManager.getLogger(AuthServiceImpl.class.getName());

    private final UserDAO userDAO;

    public AuthServiceImpl() {
        userDAO = DAOFactory.getUserAuthDao();
        logger.info("Getting user auth instance!");
    }

    /**
     * Implementation of the new user registration method.
     * @param firstName username
     * @param lastName last name of the user
     * @param email the user's email
     * @param password user password (array of characters)
     * @return true if registration was successful, otherwise false
     */
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

    /**
     * Implementation of the user authorization method.
     * @param email the user's email
     * @param password user password (array of characters)
     * @return a user object if authentication succeeds, or null if the user is not found or authentication fails
     */
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
