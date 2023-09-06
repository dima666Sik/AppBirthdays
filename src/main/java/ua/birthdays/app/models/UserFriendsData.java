package ua.birthdays.app.models;

import java.util.Objects;

public class UserFriendsData {
    private final FriendBirthdayDate friendBirthdayDate;
    private final AboutFriend aboutFriend;
    private final User user;

    public UserFriendsData(FriendBirthdayDate friendBirthdayDate, AboutFriend aboutFriend, User user) {
        this.friendBirthdayDate = friendBirthdayDate;
        this.aboutFriend = aboutFriend;
        this.user = user;
    }

    public FriendBirthdayDate getFriendBirthdayDate() {
        return friendBirthdayDate;
    }

    public AboutFriend getAboutFriend() {
        return aboutFriend;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFriendsData that = (UserFriendsData) o;
        return Objects.equals(friendBirthdayDate, that.friendBirthdayDate) && Objects.equals(aboutFriend, that.aboutFriend) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendBirthdayDate, aboutFriend, user);
    }

    @Override
    public String toString() {
        return "UserFriendsData{" +
                "friendBirthdayDate=" + friendBirthdayDate +
                ", aboutFriend=" + aboutFriend +
                ", user=" + user +
                '}';
    }
}
