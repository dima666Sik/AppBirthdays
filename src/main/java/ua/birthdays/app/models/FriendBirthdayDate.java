package ua.birthdays.app.models;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;

public class FriendBirthdayDate {
    private final Month monthBirthdayDate;
    private final MonthDay monthDayBirthdayDate;
    private final Year yearBirthdayDate;

    private final Integer hour;
    private final Integer minutes;

    private final Integer remindedCountDaysBeforeBirthday;

    public FriendBirthdayDate(Month monthBirthdayDate, MonthDay monthDayBirthdayDate, Year yearBirthdayDate, Integer hour, Integer minutes, Integer remindedCountDaysBeforeBirthday) {
        this.monthBirthdayDate = monthBirthdayDate;
        this.monthDayBirthdayDate = monthDayBirthdayDate;
        this.yearBirthdayDate = yearBirthdayDate;
        this.hour = hour;
        this.minutes = minutes;
        this.remindedCountDaysBeforeBirthday = remindedCountDaysBeforeBirthday;
    }

    public Month getMonthBirthdayDate() {
        return monthBirthdayDate;
    }

    public MonthDay getMonthDayBirthdayDate() {
        return monthDayBirthdayDate;
    }

    public Year getYearBirthdayDate() {
        return yearBirthdayDate;
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Integer getRemindedCountDaysBeforeBirthday() {
        return remindedCountDaysBeforeBirthday;
    }
}
