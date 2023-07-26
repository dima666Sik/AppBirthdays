package ua.birthdays.app.dao.query;

public class QueryUser {

    public static String createTableUser() {
        return "create table users(\n" +
                "   id_user bigint(10) unsigned AUTO_INCREMENT NOT NULL,\n" +
                "   first_name varchar(60),\n" +
                "   last_name varchar(60),\n" +
                "   email varchar(100),\n" +
                "   password varchar(100),\n" +
                "   PRIMARY KEY(id_user)\n" +
                ");";
    }

    public static String createUser() {
        return "insert into users (first_name, last_name, email, password) values (?,?,?,?);";
    }

    public static String findUserByEmailAndPassword() {
        return "select * from users where email = (?) and password = (?);";
    }
}
