package ua.birthdays.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class FriendBirthdayDate {
    private final LocalDate friendDate;

    private final int remindedFriendHour;
    private final int remindedFriendMinutes;

    private final int remindedCountDaysBeforeBirthday;

    public FriendBirthdayDate(LocalDate friendDate, int remindedFriendHour, int remindedFriendMinutes, int remindedCountDaysBeforeBirthday) {
        this.friendDate = friendDate;
        this.remindedFriendHour = remindedFriendHour;
        this.remindedFriendMinutes = remindedFriendMinutes;
        this.remindedCountDaysBeforeBirthday = remindedCountDaysBeforeBirthday;
    }

    public LocalDate getFriendDate() {
        return friendDate;
    }

    public int getRemindedFriendHour() {
        return remindedFriendHour;
    }

    public int getRemindedFriendMinutes() {
        return remindedFriendMinutes;
    }

    public int getRemindedCountDaysBeforeBirthday() {
        return remindedCountDaysBeforeBirthday;
    }

    @Override
    public String toString() {
        return "FriendBirthdayDate{" +
                "friendDate=" + friendDate +
                ", remindedFriendHour=" + remindedFriendHour +
                ", remindedFriendMinutes=" + remindedFriendMinutes +
                ", remindedCountDaysBeforeBirthday=" + remindedCountDaysBeforeBirthday +
                '}';
    }
}
