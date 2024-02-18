package ua.birthdays.app.service;

import ua.birthdays.app.model.User;

import java.util.Optional;

public interface AuthService {
    boolean registration(final String firstName, final String lastName, final String email, final char[] password);

    Optional<User> authorization(final String email, final char[] password);

}
