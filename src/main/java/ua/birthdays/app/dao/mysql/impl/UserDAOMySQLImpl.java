package ua.birthdays.app.dao.mysql.impl;

import ua.birthdays.app.dao.env.EnumDBNameTables;
import ua.birthdays.app.dao.util.DBConnector;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.dao.interfaces.UserDAO;

import ua.birthdays.app.dao.query.QueryUser;
import ua.birthdays.app.dao.util.UtilDAO;
import ua.birthdays.app.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAOMySQLImpl implements UserDAO {
    private final static Logger logger = LogManager.getLogger(UserDAOMySQLImpl.class.getName());

    @Override
    public boolean createUser(final User user) throws DAOException {

        if (!UtilDAO.isTableExists(EnumDBNameTables.USER_TABLE.getEnumDBEnvironment())) {
            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(QueryUser.createTableUser())
            ) {
                statement.executeUpdate();
                logger.info("Create table \"users\" was successful!");
            } catch (SQLException e) {
                logger.info("Error table \"users\" create!", e);
                throw new DAOException("Error table \"users\" create!", e);
            }
        }

        if (userIsExist(user)) {
            logger.info("Such user is defined, please change login...");
            return false;
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUser.createUser())
        ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            logger.info("Create user was successful!");
        } catch (SQLException e) {
            logger.error("Cannot create user!", e);
            throw new DAOException("Cannot create user!", e);
        }

        return true;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DAOException {
        if (!UtilDAO.isTableExists(EnumDBNameTables.USER_TABLE.getEnumDBEnvironment())) {
            logger.info("Read user wasn't successful! Table \" " +
                    EnumDBNameTables.USER_TABLE.getEnumDBEnvironment() + "\" not exists!");
            throw new DAOException("Read user wasn't successful! Table \" " +
                    EnumDBNameTables.USER_TABLE.getEnumDBEnvironment() + "\" not exists!");
        }

        User user = null;

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUser.findUserByEmailAndPassword())
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    user = new User(firstName, lastName, email, password);
                }
                logger.info("Read user was successful!");
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        return user;
    }

    private boolean userIsExist(User user) throws DAOException {

        boolean flag = false;

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueryUser.findUserByEmailAndPassword())
        ) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            try (ResultSet resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) flag = true;
            }
            logger.info("Check user exist was successful!");
        } catch (SQLException e) {
            logger.error("Cannot check user!", e);
            throw new DAOException("Cannot check user!", e);
        }
        return flag;
    }

}
