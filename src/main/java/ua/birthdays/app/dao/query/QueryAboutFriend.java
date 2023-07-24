package ua.birthdays.app.dao.query;

public class QueryAboutFriend {

    public static String createTableAboutFriend() {
        return "create table about_friend(\n" +
                "   id_about_friend bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
                "   name_friend varchar(60),\n" +
                "   PRIMARY KEY(id_about_friend)\n";
    }

    public static String createAboutFriend() {
        return "insert into about_friend (name_friend) values (?);";
    }

    public static String findAboutFriendByNameFriend() {
        return "select * about_friend where name_friend in (?)";
    }
}
