package ua.birthdays.app.dao.query;

public class QueryUserFriendsData {

    public static String createTableUserFriendsData() {
        return "create table user_friends_data(\n" +
                "   id_user_friends_data bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
                "   id_about_friend bigint(10)\n" +
                "   id_friend_birthday_date bigint(10)\n" +
                "   id_user bigint(10)\n" +
                "   PRIMARY KEY(id_user_friends_data)\n" +
                "   FOREIGN KEY (id_about_friend) REFERENCES about_friend(id_about_friend)\n" +
                "   FOREIGN KEY (id_friend_birthday_date) REFERENCES friend_birthday_date(id_friend_birthday_date)\n" +
                "   FOREIGN KEY (id_user) REFERENCES users(id_user)\n" +
                ");";
    }

    public static String createUserFriendsData() {
        return "insert into user_friends_data (id_about_friend, id_friend_birthday_date, id_user) values (?,?,?);";
    }

}
