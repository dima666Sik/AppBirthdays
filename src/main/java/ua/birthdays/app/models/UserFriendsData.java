package ua.birthdays.app.models;

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
}
