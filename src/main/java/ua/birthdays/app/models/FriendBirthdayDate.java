package ua.birthdays.app.models;

import ua.birthdays.app.dao.env.PeriodTimeEnum;

import java.time.LocalDate;
import java.util.Objects;

public class FriendBirthdayDate {
    private final LocalDate friendDate;

    private final int remindedFriendHour;
    private final int remindedFriendMinutes;
    private final PeriodTimeEnum periodTimeEnum;

    private final int remindedCountDaysBeforeBirthday;

    public FriendBirthdayDate(LocalDate friendDate, int remindedFriendHour, int remindedFriendMinutes, PeriodTimeEnum periodTimeEnum, int remindedCountDaysBeforeBirthday) {
        this.friendDate = friendDate;
        this.remindedFriendHour = remindedFriendHour;
        this.remindedFriendMinutes = remindedFriendMinutes;
        this.periodTimeEnum = periodTimeEnum;
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

    public PeriodTimeEnum getPeriodTimeEnum() {
        return periodTimeEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendBirthdayDate that = (FriendBirthdayDate) o;
        return remindedFriendHour == that.remindedFriendHour && remindedFriendMinutes == that.remindedFriendMinutes && remindedCountDaysBeforeBirthday == that.remindedCountDaysBeforeBirthday && Objects.equals(friendDate, that.friendDate) && periodTimeEnum == that.periodTimeEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendDate, remindedFriendHour, remindedFriendMinutes, periodTimeEnum, remindedCountDaysBeforeBirthday);
    }

    @Override
    public String toString() {
        return "FriendBirthdayDate{" +
                "friendDate=" + friendDate +
                ", remindedFriendHour=" + remindedFriendHour +
                ", remindedFriendMinutes=" + remindedFriendMinutes +
                ", periodTimeEnum=" + periodTimeEnum +
                ", remindedCountDaysBeforeBirthday=" + remindedCountDaysBeforeBirthday +
                '}';
    }
}
