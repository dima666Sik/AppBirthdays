package ua.birthdays.app.dao.mysql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.birthdays.app.dao.UserDAO;
import ua.birthdays.app.dao.env.EnumDBNameTable;
import ua.birthdays.app.dao.exceptions.DataAddingException;
import ua.birthdays.app.dao.exceptions.DataReadingException;
import ua.birthdays.app.dao.query.QueryUser;
import ua.birthdays.app.dao.util.DBConnector;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.model.User;

import java.sql.SQLException;
import java.util.Optional;


/**
 * This class is an implementation of the UserDAO interface.
 * It uses MySQL as a database.
 */
public class UserDAOImpl implements UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class.getName());

    /**
     * Creates a new user in the database.
     *
     * @param user the user to create
     * @return true if the user was created, false if a user with the same email already exists
     * @throws DataAddingException if there was an error creating the user
     */
    @Override
    public boolean createUser(final User user) {
        UtilDAO.isUserTableExists();

        if (userIsExist(user)) {
            logger.info("Such user is defined, please change login...");
            return false;
        }

        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUser.CREATE_USER)
        ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            logger.info("Create user was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create user!", e);
            throw new DataAddingException("Cannot create user!", e);
        }

        return true;
    }

    /**
     * Finds a user in the database by email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return the user, if found, otherwise an empty optional
     * @throws DataReadingException if there was an error reading the user
     */
    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        if (!UtilDAO.isTableExists(EnumDBNameTable.USER_TABLE.getEnumDBEnvironment())) {
            logger.info("Read user wasn't successful! Table \"{}\" not exists!",
                    EnumDBNameTable.USER_TABLE.getEnumDBEnvironment());
            throw new DataReadingException("Read user wasn't successful! Table \" " +
                    EnumDBNameTable.USER_TABLE.getEnumDBEnvironment() + "\" not exists!");
        }

        Optional<User> user = Optional.empty();

        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUser.FIND_USER_BY_EMAIL_AND_PASSWORD)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    user = Optional.of(new User(firstName, lastName, email, password));
                }
                logger.info("Read user was successful!");
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        return user;
    }

    /**
     * Checks if a user with the given email exists in the database.
     *
     * @param user the user to check
     * @return true if the user exists, false otherwise
     * @throws DataReadingException if there was an error reading the user
     */
    private boolean userIsExist(User user) {

        boolean flag = false;

        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement(QueryUser.FIND_USER_BY_EMAIL_AND_PASSWORD)
        ) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            var resultSet = statement.executeQuery();

            if (resultSet.next()) flag = true;

            logger.info("Check user exist was successful!");
        } catch (SQLException e) {
            logger.error("Cannot check user!", e);
            throw new DataReadingException("Cannot check user!", e);
        }
        return flag;
    }
}


