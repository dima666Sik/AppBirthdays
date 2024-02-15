package ua.birthdays.app.dao.query;

public class QueryFriendBirthdayDate {
    private QueryFriendBirthdayDate() {
    }

    public static final String CREATE_TABLE_FRIEND_BIRTHDAY_DATE =
            "create table friend_birthday_date(\n" +
                    "   id_friend_birthday_date bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
                    "   friend_date date,\n" +
                    "   reminded_friend_hour int(2),\n" +
                    "   reminded_friend_minutes int(2),\n" +
                    "   reminded_period_time_enum varchar(2),\n" +
                    "   reminded_count_days_before_birthday int(3),\n" +
                    "   PRIMARY KEY(id_friend_birthday_date)\n" +
                    "   );";

    public static final String INSERT_INTO_FRIEND_BIRTHDAY_DATE =
            "insert into friend_birthday_date (friend_date, reminded_friend_hour, " +
                    "reminded_friend_minutes, reminded_period_time_enum, reminded_count_days_before_birthday) " +
                    "values (?,?,?,?,?);";


}
