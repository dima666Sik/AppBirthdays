package ua.birthdays.app.dao.query;

public class QueryFriendBirthdayDate {

    public static String createTableFriendBirthdayDate() {
        return "create table friend_birthday_date(\n" +
                "   id_friend_birthday_date bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
                "   month_day_birthday_date int(2),\n" +
                "   month_birthday_date int(2),\n" +
                "   year_birthday_date int(4),\n" +
                "   reminded_hour int(2),\n" +
                "   reminded_minutes int(2),\n" +
                "   reminded_count_days_before_birthday int(3),\n" +
                "   PRIMARY KEY(id_friend_birthday_date)\n";
    }

    public static String createFriendBirthdayDate() {
        return "insert into friend_birthday_date (month_day_birthday_date, month_birthday_date, year_birthday_date," +
                " hour, minutes, reminded_count_days_before_birthday) values (?,?,?,?,?,?);";
    }

    public static String findFriendBirthdayDateByAllInformation() {
        return "select * friend_birthday_date where month_day_birthday_date in (?) and " +
                "month_birthday_date in (?) and year_birthday_date in (?) and reminded_hour in (?) and " +
                "reminded_minutes in (?) and reminded_count_days_before_birthday in (?)";
    }
}
