package ua.birthdays.app.dao.interfaces;

import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.User;

public interface UserDAO {
    boolean createUser(final User user) throws DAOException;

    User findUserByEmailAndPassword(final String email, final String password) throws DAOException;
}
