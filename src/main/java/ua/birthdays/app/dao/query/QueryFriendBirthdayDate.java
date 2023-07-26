package ua.birthdays.app.dao.query;

public class QueryFriendBirthdayDate {

    public static String createTableFriendBirthdayDate() {
        return "create table friend_birthday_date(\n" +
                "   id_friend_birthday_date bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
                "   friend_date date,\n" +
                "   reminded_friend_hour int(2),\n" +
                "   reminded_friend_minutes int(2),\n" +
                "   reminded_count_days_before_birthday int(3),\n" +
                "   PRIMARY KEY(id_friend_birthday_date)\n" +
                "   );";
    }

    public static String createFriendBirthdayDate() {
        return "insert into friend_birthday_date (friend_date, reminded_friend_hour, " +
                "reminded_friend_minutes, reminded_count_days_before_birthday) values (?,?,?,?);";
    }

}
