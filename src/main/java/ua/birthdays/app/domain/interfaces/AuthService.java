package ua.birthdays.app.domain.interfaces;

import ua.birthdays.app.models.User;

public interface AuthService {
    boolean registration(String firstName, String lastName, String email, char[] password);
    User authorization(String email, char[] password);

}
