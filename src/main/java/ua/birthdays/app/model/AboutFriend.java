package ua.birthdays.app.model;

import java.util.Objects;

public class AboutFriend {
    private final String nameFriend;

    public AboutFriend(String nameFriend) {
        this.nameFriend = nameFriend;
    }

    public String getNameFriend() {
        return nameFriend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AboutFriend that = (AboutFriend) o;
        return Objects.equals(nameFriend, that.nameFriend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameFriend);
    }

    @Override
    public String toString() {
        return "AboutFriend{" +
                "nameFriend='" + nameFriend + '\'' +
                '}';
    }
}
