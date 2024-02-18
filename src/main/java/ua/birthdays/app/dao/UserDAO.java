package ua.birthdays.app.dao;

import ua.birthdays.app.model.User;

import java.util.Optional;

public interface UserDAO {
    boolean createUser(final User user);

    Optional<User> findUserByEmailAndPassword(final String email, final String password);
}
