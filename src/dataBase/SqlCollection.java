package dataBase;

public class SqlCollection {
    public static final String CREATE_SETTINGS =
            "CREATE TABLE IF NOT EXISTS Settings (" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " key varchar(255)," +
                    " value INTEGER);";

    public static final String SET_FIRST_PRODUCT_ID =
            "INSERT INTO Settings ('key', 'value') " +
                    "VALUES ('last_product_id', 1);";

    public static final String SET_FIRST_CATEGORY_ID =
            "INSERT INTO Settings ('key', 'value') " +
                    "VALUES ('last_category_id', 1);";

    public static final String CREATE_PRODUCTS =
            "CREATE TABLE IF NOT EXISTS Products " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name varchar(255), " +
                    "desc text, " +
                    "price money, " +
                    "sale_price money, " +
                    "sale_from varchar(12), " +
                    "sale_to varchar(12), " +
                    "qty int, " +
                    "category_id int)";

    public static final String CREATE_CATEGORIES =
            "CREATE TABLE IF NOT EXISTS Categories " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name varchar(255), " +
                    "desc text); ";
}