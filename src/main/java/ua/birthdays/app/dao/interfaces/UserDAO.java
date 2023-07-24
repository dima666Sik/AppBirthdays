package ua.birthdays.app.dao.interfaces;


import ua.birthdays.app.dao.exceptions.DAOException;
import ua.birthdays.app.models.User;

public interface UserDAO {
    boolean createUser(User user) throws DAOException;

    User findUserByEmailAndPassword(String email, String password) throws DAOException;
}
