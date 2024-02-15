package ua.birthdays.app.dao.query;

public class QueryAboutFriend {
    private QueryAboutFriend() {
    }

    public static final String CREATE_TABLE_ABOUT_FRIEND =
            "CREATE TABLE about_friend (\n" +
                    "   id_about_friend bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
                    "   name_friend varchar(60),\n" +
                    "   PRIMARY KEY(id_about_friend)\n" +
                    ");";

    public static final String INSERT_INTO_ABOUT_FRIEND =
            "INSERT INTO about_friend (name_friend) " +
                    "VALUES (?);";
}
