package ua.birthdays.app.domain;

import ua.birthdays.app.models.User;

import java.util.Optional;

public interface AuthService {
    boolean registration(final String firstName, final String lastName, final String email, final char[] password);

    Optional<User> authorization(final String email, final char[] password);

}
