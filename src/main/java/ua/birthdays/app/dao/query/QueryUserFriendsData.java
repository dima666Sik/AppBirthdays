package ua.birthdays.app.dao.query;

public class QueryUserFriendsData {
    private QueryUserFriendsData() {
    }

    public static final String CREATE_TABLE_USER_FRIENDS_DATA = "create table user_friends_data(\n" +
            "   id_user_friends_data bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
            "   id_about_friend bigint(10) unsigned,\n" +
            "   id_friend_birthday_date bigint(10) unsigned,\n" +
            "   id_user bigint(10) unsigned,\n" +
            "   PRIMARY KEY(id_user_friends_data),\n" +
            "   FOREIGN KEY (id_about_friend) REFERENCES about_friend(id_about_friend),\n" +
            "   FOREIGN KEY (id_friend_birthday_date) REFERENCES friend_birthday_date(id_friend_birthday_date),\n" +
            "   FOREIGN KEY (id_user) REFERENCES users(id_user)\n" +
            ");";

    public static final String INSERT_USER_FRIENDS_DATA = "insert into user_friends_data (id_about_friend, id_friend_birthday_date, id_user) values (?,?,?);";

    public static final String FIND_USER_FRIENDS_DATA_BY_USER_ID = "select friend_birthday_date.*, about_friend.* \n" +
            "from user_friends_data \n" +
            "left join friend_birthday_date on user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date \n" +
            "left join about_friend on user_friends_data.id_about_friend = about_friend.id_about_friend \n" +
            "where user_friends_data.id_user=(?);";

    public static final String FIND_USER_FRIENDS_DATA_BY_USER_ID_ASCENDING_NAME_FRIEND =
            "select friend_birthday_date.*, about_friend.* \n" +
                    "from user_friends_data \n" +
                    "left join friend_birthday_date on user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date \n" +
                    "left join about_friend on user_friends_data.id_about_friend = about_friend.id_about_friend \n" +
                    "where user_friends_data.id_user=(?) " +
                    "order by about_friend.name_friend ASC;";

    public static final String FIND_USER_FRIENDS_DATA_BY_USER_ID_DESCENDING_NAME_FRIEND =
            "select friend_birthday_date.*, about_friend.* \n" +
                    "from user_friends_data \n" +
                    "left join friend_birthday_date on user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date \n" +
                    "left join about_friend on user_friends_data.id_about_friend = about_friend.id_about_friend \n" +
                    "where user_friends_data.id_user=(?) " +
                    "order by about_friend.name_friend DESC;";

    public static final String FIND_USER_FRIENDS_DATA_BY_USER_ID_ASCENDING_FRIEND_BIRTHDAY_DATE =
            "select friend_birthday_date.*, about_friend.* \n" +
                    "from user_friends_data \n" +
                    "left join friend_birthday_date on user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date \n" +
                    "left join about_friend on user_friends_data.id_about_friend = about_friend.id_about_friend \n" +
                    "where user_friends_data.id_user=(?) " +
                    "order by friend_birthday_date.friend_date ASC;";

    public static final String FIND_USER_FRIENDS_DATA_BY_USER_ID_DESCENDING_FRIEND_BIRTHDAY_DATE =
            "select friend_birthday_date.*, about_friend.* \n" +
                    "from user_friends_data \n" +
                    "left join friend_birthday_date on user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date \n" +
                    "left join about_friend on user_friends_data.id_about_friend = about_friend.id_about_friend \n" +
                    "where user_friends_data.id_user=(?) " +
                    "order by friend_birthday_date.friend_date DESC;";

    public static final String EXISTS_BY_USER_ID_AND_FRIEND_NAME_AND_FRIEND_DATE =
            "select id_user_friends_data\n" +
                    "from user_friends_data \n" +
                    "left join friend_birthday_date on user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date \n" +
                    "left join about_friend on user_friends_data.id_about_friend = about_friend.id_about_friend \n" +
                    "where user_friends_data.id_user=(?) and about_friend.name_friend=(?) and friend_birthday_date.friend_date=(?);";

    public static final String UPDATE_USER_FRIENDS_DATA_BY_ID_USER_FRIEND_DATE =
            "UPDATE user_friends_data\n" +
                    "JOIN friend_birthday_date ON user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date\n" +
                    "JOIN about_friend ON user_friends_data.id_about_friend = about_friend.id_about_friend\n" +
                    "SET friend_birthday_date.friend_date = (?),\n" +
                    "\tfriend_birthday_date.reminded_friend_hour = (?),\n" +
                    "\tfriend_birthday_date.reminded_friend_minutes = (?),\n" +
                    "\tfriend_birthday_date.reminded_period_time_enum = (?),\n" +
                    "\tfriend_birthday_date.reminded_count_days_before_birthday = (?),\n" +
                    "\tabout_friend.name_friend = (?)\n" +
                    "WHERE user_friends_data.id_user_friends_data = (?)\n";

    public static final String DELETE_USER_FRIENDS_DATA_BY_ID_USER_FRIEND_DATE =
            "delete user_friends_data, friend_birthday_date, about_friend\n" +
                    "FROM user_friends_data\n" +
                    "JOIN friend_birthday_date ON user_friends_data.id_friend_birthday_date = friend_birthday_date.id_friend_birthday_date\n" +
                    "JOIN about_friend ON user_friends_data.id_about_friend = about_friend.id_about_friend\n" +
                    "WHERE \n" +
                    "\tuser_friends_data.id_user_friends_data = (?)\n";

}
