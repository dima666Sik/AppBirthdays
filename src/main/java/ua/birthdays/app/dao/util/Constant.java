package ua.birthdays.app.dao.util;

public final class Constant {
    private Constant() {
    }

    public static final String DB_NAME_PROP_FILE_KEY = "db.properties";
    public static final String APP_BIRTHDAYS_DATA_DB_URL = "app.birthdays.data.db.url";
    public static final String APP_BIRTHDAYS_DATA_USER_NAME = "app.birthdays.data.user.name";
    public static final String APP_BIRTHDAYS_DATA_PASSWORD = "app.birthdays.data.password";
    public static final String APP_BIRTHDAYS_DATA_DB_NAME = "app.birthdays.data.db.name";
    public static final String COUNT_CONN_POOL_KEY = "count.conn.pool";

    public static final int DEFAULT_COUNT_CONN_POOL = 10;
}
