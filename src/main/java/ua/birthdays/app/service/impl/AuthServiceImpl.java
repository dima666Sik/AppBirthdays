package ua.birthdays.app.service.impl;

import ua.birthdays.app.dao.UserDAO;
import ua.birthdays.app.dao.util.DAOFactory;
import ua.birthdays.app.service.AuthService;
import ua.birthdays.app.service.util.Encryption;
import ua.birthdays.app.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Implementation of {@link AuthService} interface for user authentication and registration.
 */
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class.getName());

    private final UserDAO userDAO;

    public AuthServiceImpl() {
        userDAO = DAOFactory.getUserAuthDao();
        logger.info("Getting user auth instance!");
    }

    /**
     * Implementation of the new user registration method.
     *
     * @param firstName username
     * @param lastName  last name of the user
     * @param email     the user's email
     * @param password  user password (array of characters)
     * @return true if registration was successful, otherwise false
     */
    @Override
    public boolean registration(final String firstName,
                                final String lastName,
                                final String email,
                                final char[] password) {

        var user = new User(firstName, lastName, email, Encryption.encryptionSHA256(password));
        return userDAO.createUser(user);
    }

    @Override
    public Optional<User> authorization(final String email, final char[] password) {
        var user
                = userDAO.findUserByEmailAndPassword(email, Encryption.encryptionSHA256(password));

        if (user.isEmpty()) {
            logger.warn("User is empty! Finding user wasn't successful!");
        }

        return user;
    }


}
