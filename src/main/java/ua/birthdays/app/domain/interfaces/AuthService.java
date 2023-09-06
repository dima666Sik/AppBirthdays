package ua.birthdays.app.domain.interfaces;

import ua.birthdays.app.models.User;

public interface AuthService {
    boolean registration(final String firstName, final String lastName, final String email, final char[] password);

    User authorization(final String email, final char[] password);

}
